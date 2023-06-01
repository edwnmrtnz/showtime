package com.github.edwnmrtnz.showtime.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.github.amaterasu.scopey.scopey
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.app.helper.EspressoIdlingResource
import com.github.edwnmrtnz.showtime.app.helper.ScrollStateHolder
import com.github.edwnmrtnz.showtime.app.helper.ignore
import com.github.edwnmrtnz.showtime.app.ui.details.MovieDetailsFragment
import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.databinding.FragmentMovielistBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class HomeMoviesFragment : Fragment(), HomeMoviesView {

    private var _binding: FragmentMovielistBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SectionMoviesAdapter

    @Inject
    lateinit var provider: Provider<HomeMoviesPresenter>
    private val presenter by scopey { provider.get() }

    private lateinit var scrollStateHolder: ScrollStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scrollStateHolder = ScrollStateHolder(savedInstanceState)

        adapter = SectionMoviesAdapter(scrollStateHolder) { itemView, movie ->
            onItemMovieClicked(itemView, movie)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scrollStateHolder.onSaveInstanceState(outState)
    }

    private var clickedItem: View? = null
    private fun onItemMovieClicked(view: View, movie: MoviePreview) {
        this.clickedItem = view
        presenter.onItemClicked(movie)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovielistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSectionedMovies.layoutManager = LinearLayoutManager(
            requireContext(),
            VERTICAL,
            false
        )
        binding.rvSectionedMovies.setHasFixedSize(true)
        binding.rvSectionedMovies.adapter = adapter

        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onStart() {
        super.onStart()
        EspressoIdlingResource.increment()
        presenter.bind(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun render(state: HomeMoviesUiState) {
        if (state.navigate != null) {
            handleNavigation(state.navigate)
            return
        }

        if (state.dialog != null) {
            handleDialogs(state.dialog)
        }

        adapter.submitList(state.movies) {
            if (state.movies.isNotEmpty()) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    private fun handleNavigation(navigation: HomeMoviesUiState.Navigate) {
        when (navigation) {
            is HomeMoviesUiState.Navigate.Details -> {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(
                        R.id.flContainer,
                        MovieDetailsFragment.newInstance(
                            navigation.movie.id,
                            navigation.movie.thumbnail
                        ),
                        "movie_list"
                    )
                    addSharedElement(clickedItem!!, navigation.movie.id.toString())
                    addToBackStack(null)
                }
            }
        }
        presenter.onNavigationHandled()
    }

    private fun handleDialogs(dialog: HomeMoviesUiState.Dialog) {
        val message = when (dialog) {
            is HomeMoviesUiState.Dialog.ErrorOnOpeningMovie -> dialog.message
            is HomeMoviesUiState.Dialog.ErrorOnSetup -> dialog.message
        }

        MaterialAlertDialogBuilder(requireContext())
            .setCancelable(false)
            .setTitle("Oops")
            .setMessage(message)
            .setPositiveButton("Retry") { _, _ ->
                when (dialog) {
                    is HomeMoviesUiState.Dialog.ErrorOnOpeningMovie -> {
                        presenter.onRetryOpeningMovie(dialog.movie)
                    }

                    is HomeMoviesUiState.Dialog.ErrorOnSetup -> {
                        presenter.onRetryLoading()
                    }
                }
            }
            .setNegativeButton("No") { _, _ ->
                when (dialog) {
                    is HomeMoviesUiState.Dialog.ErrorOnOpeningMovie -> ignore()
                    is HomeMoviesUiState.Dialog.ErrorOnSetup -> requireActivity().finishAffinity()
                }
            }.show()
        presenter.onDialogDisplayed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

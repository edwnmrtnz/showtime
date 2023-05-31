package com.github.edwnmrtnz.showtime.app.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.amaterasu.scopey.scopey
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.databinding.FragmentMovieDetailsBinding
import com.google.android.material.chip.Chip
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(), MovieDetailsView {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var provider: Provider<MovieDetailsPresenter>

    private val presenter by scopey {
        provider.get()
    }
    private val movieCastAdapter = MovieCastAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater
            .from(requireContext())
            .inflateTransition(R.transition.shared_image)
        presenter.set(requireArguments().getInt(KEY_MOVIE_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.ivMovieBanner, id.toString())

        // postponeEnterTransition()

        Picasso
            .get()
            .load(requireArguments().getString(KEY_THUMBNAIL))
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(binding.ivMovieBanner)

        // (view.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }

        binding.rvMovieCasts.adapter = movieCastAdapter
        binding.rvMovieCasts.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun render(state: MovieDetailsUiState) {
        if (state.movie == null) return

        val movie = state.requireMovie()

        binding.tvMovieTitle.text = movie.title
        binding.tvMovieOverview.text = movie.overview

        movie.keywords.forEach { cast ->
            binding.cgKeywords.addView(Chip(requireContext()).apply { text = cast })
        }

        movieCastAdapter.submitList(movie.cast)

        lifecycleScope.launch {
            delay(FAKE_DELAY)
            showHiddenViews()
        }
    }

    private fun showHiddenViews() {
        listOf(
            binding.tvMovieTitle,
            binding.cgKeywords,
            binding.tvMovieOverview,
            binding.rvMovieCasts,
            binding.tvMovieOverviewTitle,
            binding.tvCastTitle
        ).forEach { it.visibility = View.VISIBLE }
    }

    companion object {
        private const val KEY_MOVIE_ID = "key_movie_id"
        private const val KEY_THUMBNAIL = "key_thumbnail"
        private const val FAKE_DELAY = 100L
        fun newInstance(movieId: Int, thumbnail: String): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                val bundle = Bundle().apply {
                    putInt(KEY_MOVIE_ID, movieId)
                    putString(KEY_THUMBNAIL, thumbnail)
                }
                arguments = bundle
            }
        }
    }
}

package com.github.edwnmrtnz.showtime.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.updatePadding
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.core.Movie
import com.github.edwnmrtnz.showtime.core.MovieDataSource
import com.github.edwnmrtnz.showtime.databinding.FragmentMovielistBinding
import com.github.edwnmrtnz.showtime.ui.details.MovieDetailsFragment

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovielistBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter : SectionMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SectionMoviesAdapter { itemView, movie ->
            onItemMovieClicked(itemView, movie)
        }
    }

    private fun onItemMovieClicked(view: View, movie: Movie) {
        val fragment = MovieDetailsFragment.newInstance(movie.id)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addSharedElement(view, movie.id.toString())
            replace(R.id.flContainer, fragment, "tag_movie_list")
            addToBackStack(null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovielistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        binding.rvMovies.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvMovies.adapter = adapter
        adapter.submitList(MovieDataSource.items)

        // Start the transition once all views have been measured and laid out
        (view.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
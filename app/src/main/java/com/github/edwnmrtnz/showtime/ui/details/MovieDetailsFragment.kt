package com.github.edwnmrtnz.showtime.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.allViews
import androidx.lifecycle.lifecycleScope
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.core.MovieDataSource
import com.github.edwnmrtnz.showtime.databinding.FragmentMovieDetailsBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hides = listOf(
            binding.tvMovieTitle,
            binding.cgKeywords,
            binding.tvMovieOverview,
            binding.rvMovieCasts,
            binding.tvMovieOverviewTitle,
            binding.tvCastTitle
        )
        hides.forEach { it.visibility = View.INVISIBLE }

        val id = requireArguments().getInt(KEY_MOVIE_ID)
        ViewCompat.setTransitionName(binding.ivMovieBanner, id.toString())
        postponeEnterTransition()

        binding.ivMovieBanner.setImageResource(R.drawable.sample_item_movie)
        startPostponedEnterTransition()

        binding.tvMovieTitle.text = "Avengers: Endgame"
        binding.tvMovieOverview.text = resources.getString(R.string.text_sample_overview)

        binding.cgKeywords.addView(Chip(requireContext()).apply { text = "Action" })
        binding.cgKeywords.addView(Chip(requireContext()).apply { text = "Fantasy" })

        val movie = MovieDataSource.items.map { it.movies }.flatten().first { it.id == id }
        val adapter = MovieCastAdapter(movie.cast)
        binding.rvMovieCasts.adapter = adapter

        lifecycleScope.launch {
            delay(100)
            hides.forEach { it.visibility = View.VISIBLE }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_MOVIE_ID = "key_movie_id"
        fun newInstance(movieId : Int) : MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                val bundle = Bundle().apply { putInt(KEY_MOVIE_ID, movieId) }
                arguments = bundle
            }
        }
    }
}


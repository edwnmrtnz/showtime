package com.github.edwnmrtnz.showtime.app

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.commit
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.app.ui.details.MovieDetailsFragment
import com.github.edwnmrtnz.showtime.app.ui.home.HomeMoviesFragment
import com.github.edwnmrtnz.showtime.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.flContainer, HomeMoviesFragment())
            }
        }

        setupSystemBarVisibility()
    }

    private fun setupSystemBarVisibility() {
        fun hideOrShow() {
            val isMovieDetail = supportFragmentManager.fragments.last() is MovieDetailsFragment
            if (isMovieDetail) {
                window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }
        }
        if (supportFragmentManager.fragments.isNotEmpty()) {
            hideOrShow()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            hideOrShow()
        }
    }
}

package com.github.edwnmrtnz.showtime

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.github.edwnmrtnz.showtime.databinding.ActivityMainBinding
import com.github.edwnmrtnz.showtime.ui.details.MovieDetailsFragment
import com.github.edwnmrtnz.showtime.ui.home.MovieListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flContainer, MovieListFragment())
            }.commit()
        }

        setupSystemBarVisibility()
    }

    private fun setupSystemBarVisibility() {
        fun hideOrShow() {
            val isMovieDetail = supportFragmentManager.fragments.last() is MovieDetailsFragment
            if(isMovieDetail) {
                window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }
        }
        if(supportFragmentManager.fragments.isNotEmpty()) {
            hideOrShow()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            hideOrShow()
        }
    }


}
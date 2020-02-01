package com.matteopasotti.whatmovie.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.view.ui.HomeMovieCategoryConstants
import com.matteopasotti.whatmovie.view.ui.HomeMoviesGalleryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val popularMoviesGalleryFragment =
            HomeMoviesGalleryFragment.newInstance(HomeMovieCategoryConstants.POPULARS)
        supportFragmentManager.beginTransaction()
            .replace(R.id.popular_movies_sections, popularMoviesGalleryFragment)
            .commit()
    }
}
package com.matteopasotti.whatmovie.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.matteopasotti.whatmovie.R
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.movies.observe(this , Observer { response ->
            if(response != null) {
                Log.d("" , "")
            }
        })

        viewModel.getPopularMovies()
    }
}
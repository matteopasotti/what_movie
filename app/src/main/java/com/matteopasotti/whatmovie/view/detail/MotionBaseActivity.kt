package com.matteopasotti.whatmovie.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.matteopasotti.whatmovie.R

class MotionBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_detail)
    }
}
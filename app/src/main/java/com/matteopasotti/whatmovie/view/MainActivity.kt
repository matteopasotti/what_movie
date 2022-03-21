package com.matteopasotti.whatmovie.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.util.CustomViewPager
import com.matteopasotti.whatmovie.view.ui.HomeMovieCategoryConstants
import com.matteopasotti.whatmovie.view.ui.TestFragment
import com.matteopasotti.whatmovie.view.ui.home.HomeFragmentPageAdapter
import com.matteopasotti.whatmovie.view.ui.home.HomeMoviesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var viewPager: CustomViewPager
    private lateinit var pagerAdapter : HomeFragmentPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottomNavigationView)
        viewPager = findViewById(R.id.viewPager)
        pagerAdapter = HomeFragmentPageAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = (pagerAdapter.count - 1)

        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    viewPager.currentItem = 0
                }
                R.id.action_search -> {
                    viewPager.currentItem = 1
                }
                R.id.action_profile -> {
                    viewPager.currentItem = 2
                }
                R.id.action_settings -> {
                    viewPager.currentItem = 3
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
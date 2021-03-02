package com.matteopasotti.whatmovie.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.view.ui.HomeMovieCategoryConstants
import com.matteopasotti.whatmovie.view.ui.TestFragment
import com.matteopasotti.whatmovie.view.ui.home.HomeMoviesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.action_home -> {
                val fragment = HomeMoviesFragment.newInstance(HomeMovieCategoryConstants.POPULARS)
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_search -> {
                val fragment = TestFragment.newInstance("SEARCH")
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                val fragment = TestFragment.newInstance("PROFILE")
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_settings -> {
                val fragment = TestFragment.newInstance("SETTINGS")
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottomNavigationView)
        bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
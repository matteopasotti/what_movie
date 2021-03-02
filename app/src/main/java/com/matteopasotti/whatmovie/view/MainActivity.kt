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
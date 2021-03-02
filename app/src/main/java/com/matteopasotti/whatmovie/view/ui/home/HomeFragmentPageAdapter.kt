package com.matteopasotti.whatmovie.view.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.matteopasotti.whatmovie.view.ui.HomeMovieCategoryConstants
import com.matteopasotti.whatmovie.view.ui.TestFragment


class HomeFragmentPageAdapter(manager: FragmentManager) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeMoviesFragment.newInstance(HomeMovieCategoryConstants.POPULARS)
            1 -> TestFragment.newInstance("SEARCH")
            2 -> TestFragment.newInstance("PROFILE")
            3 -> TestFragment.newInstance("SETTINGS")
            else -> TestFragment.newInstance("TEST")
        }
    }

    override fun getCount(): Int {
        return 4
    }

}


package com.matteopasotti.whatmovie.util

import com.matteopasotti.whatmovie.view.ui.HomeMovieCategoryConstants

class MoviesUtils {

    companion object {
        fun getHomeSectionNameByCategory(category: Int?): String {
            when(category) {
                HomeMovieCategoryConstants.POPULARS -> return "popular"
                HomeMovieCategoryConstants.UPCOMING -> return "upcoming"
            }

            return "Category not founded"
        }

        fun getMovieImageUrl(poster: String?): String {
            if(poster != null) {
                return "http://image.tmdb.org/t/p/w185$poster"
            }

            return ""
        }
    }

}


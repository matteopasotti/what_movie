package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDetail
import com.matteopasotti.whatmovie.model.MovieDetailDomainModel
import com.matteopasotti.whatmovie.model.response.MovieCreditResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.repository.MovieDetailRepository
import java.io.IOException

class GetMovieDetailsUseCase(private val movieDetailRepository: MovieDetailRepository) {

    suspend fun getRecommendedMovies(movieId: Int): MoviesDomainModel {
        return movieDetailRepository.getRecommendedMovies(movieId).toMoviesDomainModel()
    }


    suspend fun getSimilarMovies(movieId: Int): MoviesDomainModel {
        val response = movieDetailRepository.getSimilarMovies(movieId).toMoviesDomainModel()
        val validMovies = response.movies.filter { it.poster_path != null }
        return response.copy(movies = validMovies)
    }

    suspend fun getMovieCredits(movieId: Int): MovieCreditsDomainModel {
        return movieDetailRepository.getMovieCredits(movieId).toMovieCreditsDomainModel()
    }

    suspend fun getMovieDetail(movieId: Int): MovieDetailVModel {
        return movieDetailRepository.getMovieDetail(movieId).toMovieDetailVModel()
    }

    private fun Result<MovieDetail>.toMovieDetailVModel(): MovieDetailVModel {
        return when (this) {
            is Result.Success -> {
                MovieDetailVModel(
                    movieDetail = this.value.toDomainModel(),
                    errorMessage = null
                )
            }

            is Result.Error -> {
                MovieDetailVModel(
                    movieDetail = null,
                    errorMessage = "Error retrieving Movie Detail"
                )
            }
        }
    }

    private fun Result<MovieCreditResponse>.toMovieCreditsDomainModel(): MovieCreditsDomainModel {
        return when (this) {
            is Result.Success -> {
                MovieCreditsDomainModel(
                    cast = this.value.cast.filter { it.profile_path != null }.map { it.toDomainModel() },
                    errorMessage = null
                )
            }

            is Result.Error -> {
                MovieCreditsDomainModel(
                    cast = null,
                    errorMessage = "Error retrieving Movie Credits"
                )
            }
        }
    }
}

data class MovieDetailVModel(
    val movieDetail: MovieDetailDomainModel?,
    val errorMessage: String? = null
)

data class MovieCreditsDomainModel(
    val cast: List<ActorDomainModel>?,
    val errorMessage: String?
)


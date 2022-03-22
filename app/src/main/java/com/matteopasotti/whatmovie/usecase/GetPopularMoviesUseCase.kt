package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.repository.MovieRepository
import org.koin.core.KoinComponent
import java.io.IOException
import java.lang.Exception

class GetPopularMoviesUseCase(
    private val movieRepository: MovieRepository
) : KoinComponent {

    suspend fun getPopularMovies(): MoviesDomainModel {
        return movieRepository.getPopularMoviesFromApi().toMoviesDomainModel()
    }

    suspend fun getMoviesAtCinema(): MoviesDomainModel {
        return movieRepository.getMoviesAtTheatre().toMoviesDomainModel()
    }

    suspend fun getTrendingOfTheWeek(): MoviesDomainModel{
        return movieRepository.getTrendingOfTheWeek().toMoviesDomainModel()
    }

    private fun Result<BasicMovieResponse>.toMoviesDomainModel(): MoviesDomainModel {
        return when (this) {
            is Result.Success -> {
                MoviesDomainModel(
                    movies = this.value.results?.map { it.toDomainModel() } ?: emptyList(),
                    errorMessage = null
                )
            }

            is Result.Error -> {
                MoviesDomainModel(
                    movies = emptyList(),
                    errorMessage = "Error unable to retrieve movies"
                )
            }
        }
    }
}

data class MoviesDomainModel(
    val movies: List<MovieDomainModel>,
    val errorMessage: String?
)
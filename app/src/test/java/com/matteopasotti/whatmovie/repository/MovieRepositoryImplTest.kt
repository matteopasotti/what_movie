package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.PopularMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    @Mock
    internal lateinit var mockService: MovieApiInterface

    private lateinit var repository: MovieRepositoryImpl

    private val language = "en-US"

    private val page = 1

    private val api_key = BuildConfig.API_KEY

    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(mockService)
    }

    @Test
    fun `getPopularMovies fetches Movie and maps to Model`() {
        runBlocking {
            given(mockService.getPopularMovies(api_key, language, page))
                .willReturn(PopularMovieResponse(page = 1, results = listOf(DataFixtures.getMovie())))

            val result = repository.getPopularMovies()

            assertEquals(result.first(), DataFixtures.getMovie().toDomainModel())
        }
    }
}
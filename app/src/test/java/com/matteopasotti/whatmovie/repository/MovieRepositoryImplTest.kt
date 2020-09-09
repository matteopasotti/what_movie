package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.response.PopularMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
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

    @Mock
    internal lateinit var movieDao: MovieDao

    @Mock
    internal lateinit var syncRepo: SyncRepoImpl

    private lateinit var repository: MovieRepositoryImpl

    private val language = "en-US"

    private val page = 1

    private val apiKey = BuildConfig.API_KEY

    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(mockService, movieDao, syncRepo)
    }

    @Test
    fun `getPopularMovies fetches Movie and maps to Model`() {
        runBlocking {
            given(mockService.getPopularMovies(apiKey, language, page))
                .willReturn(
                    PopularMovieResponse(
                        page = 1,
                        results = listOf(DataFixtures.getMovie())
                    )
                )

            val result = repository.getPopularMovies(1)

            assertEquals(result!!.first(), DataFixtures.getMovie().toDomainModel())
        }
    }
}
package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesUseCaseTest {


    @Mock
    internal lateinit var mockMovieRepository: MovieRepositoryImpl

    private lateinit var useCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        useCase = GetPopularMoviesUseCase(mockMovieRepository)
    }

    @Test
    fun `return list of movies`() {
        runBlocking {
            val movies = listOf(DomainFixtures.getMovie(), DomainFixtures.getMovie())

            given(mockMovieRepository.getPopularMovies(1)).willReturn(movies)

            val result = useCase.execute()

            assertEquals(result, movies)
        }
    }

    @Test
    fun `filter movies without image`() {
        runBlocking {
            val movieWithImage = DomainFixtures.getMovie()
            val movieWithoutImage = DomainFixtures.getMovie(poster_path = null)
            val movies = listOf(movieWithImage)

            given(mockMovieRepository.getPopularMovies(1)).willReturn(listOf(movieWithImage, movieWithoutImage))

            val result = useCase.execute()

            assertEquals(result, movies)
        }
    }
}
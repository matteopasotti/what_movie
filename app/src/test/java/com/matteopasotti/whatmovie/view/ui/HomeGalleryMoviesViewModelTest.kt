package com.matteopasotti.whatmovie.view.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import com.matteopasotti.whatmovie.util.LiveDataTestUtil
import com.matteopasotti.whatmovie.util.TestMainCoroutineRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeGalleryMoviesViewModelTest {

    @get:Rule
    var coroutinesTestRule = TestMainCoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeGalleryMoviesViewModel

    @Mock
    internal lateinit var useCase: GetPopularMoviesUseCase


    @Before
    fun setUp() {
        viewModel = HomeGalleryMoviesViewModel(useCase)
    }

    @Test
    fun `execute getPopularMoviesUseCase`() {
        // when
        viewModel.getPopularMovies()

        // then
        runBlocking {
            verify(useCase).execute()
        }
    }

    @Test
    fun `GetPopularMovies shows and hides loading progress bar`() {
        coroutinesTestRule.testCoroutineDispatcher.runBlockingTest {
            useCase.stub {
                onBlocking { execute() }.doReturn(Result.Success(listOf(DomainFixtures.getMovie())))
            }

            viewModel.getPopularMovies()

            val isLoading = LiveDataTestUtil.getValue(viewModel.isLoading)
            assertNotNull(isLoading)
            isLoading?.let { Assert.assertFalse(it) }

        }
    }

    @Test
    fun `Given we get movies,Then we update the list`(){
        val movies = listOf(DomainFixtures.getMovie())
        coroutinesTestRule.testCoroutineDispatcher.runBlockingTest {
            useCase.stub {
                onBlocking { execute() }.doReturn(Result.Success(movies))
            }

            viewModel.getPopularMovies()

            val results = LiveDataTestUtil.getValue(viewModel.popularMovies)
            assertTrue(!results.isNullOrEmpty() && results.size == movies.size)
        }
    }

    @Test
    fun `GetPopularMovies show error dialog when we get an error from api`() {
        coroutinesTestRule.testCoroutineDispatcher.runBlockingTest {
            useCase.stub {
                onBlocking { execute() }.doReturn(Result.Error("error"))
            }

            viewModel.getPopularMovies()

            val isError = LiveDataTestUtil.getValue(viewModel.isError)
            assertNotNull(isError)
            isError?.let { assertTrue(it) }
        }
    }
}
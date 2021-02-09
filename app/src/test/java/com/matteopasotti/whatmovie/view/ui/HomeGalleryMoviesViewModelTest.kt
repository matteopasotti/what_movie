package com.matteopasotti.whatmovie.view.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import com.matteopasotti.whatmovie.util.LiveDataTestUtil
import com.matteopasotti.whatmovie.util.TestMainCoroutineRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class  HomeGalleryMoviesViewModelTest {


    @get:Rule
    var coroutinesTestRule = TestMainCoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeGalleryMoviesViewModel

    private lateinit var isLoadingLiveData: LiveData<Boolean>

    private lateinit var isErrorLiveData: LiveData<Boolean>

    @Mock
    internal lateinit var useCase: GetPopularMoviesUseCase


    @Before
    fun setUp() {

        viewModel = HomeGalleryMoviesViewModel(useCase)

        isLoadingLiveData = viewModel.isLoading()

        isErrorLiveData = viewModel.isError()

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
        viewModel.getMovies()
        coroutinesTestRule.testCoroutineDispatcher.runBlockingTest {
            useCase.stub {
                onBlocking { execute() }.doReturn(Result.Success(listOf(DomainFixtures.getMovie())))
            }

            var isLoading = isLoadingLiveData.value
            assertNotNull(isLoading)
            isLoading?.let { Assert.assertTrue(it) }

            viewModel.getPopularMovies()
            verify(useCase).execute()

            isLoading = isLoadingLiveData.value
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
        viewModel.getMovies()

        coroutinesTestRule.testCoroutineDispatcher.runBlockingTest {
            useCase.stub {
                onBlocking { execute() }.doReturn(Result.Error("Error"))
            }

            var isError = isErrorLiveData.value
            assertNotNull(isError)

            isError?.let { assertFalse(it) }

            viewModel.getPopularMovies()
            verify(useCase).execute()

            isError = isErrorLiveData.value
            assertNotNull(isError)
            isError?.let { assertTrue(it) }
        }
    }
}
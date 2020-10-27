package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.repository.ActorDetailRepositoryImpl
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetActorDetailsUseCaseTest {

    @Mock
    internal lateinit var repository: ActorDetailRepositoryImpl

    private lateinit var usecase: GetActorDetailsUseCase

    @Before
    fun setUp() {
        usecase = GetActorDetailsUseCase(repository)
    }

    @Test
    fun `return actor details`() {
        val actorDetailDomain = DomainFixtures.getActorDetailDomainModel()
        runBlocking {
            given(repository.getActorDetails(1)).willReturn(actorDetailDomain)

            val result = usecase.getActorDetails(1)

            assertEquals(result, Result.Success(actorDetailDomain))
        }
    }
}
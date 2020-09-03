package com.matteopasotti.whatmovie.model

import com.matteopasotti.whatmovie.DomainFixtures
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ActorKtTest {

    @Test
    fun `given an actor then we generate the correct actor domain model`() {

        val actor = DomainFixtures.getActor()

        val expected: ActorDomainModel = ActorDomainModel(
            actor.id, actor.name, "http://image.tmdb.org/t/p/w185${actor.profile_path}"
        )

        val result = actor.toDomainModel()

        assertEquals(result, expected)
    }

}
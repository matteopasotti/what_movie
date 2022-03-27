package com.matteopasotti.whatmovie.model

import com.matteopasotti.whatmovie.DomainFixtures
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ActorDetailResponseKtTest {

    @Test
    fun `given an actor detail then we generate the correct actor domain model`() {

        val actorDetail = DomainFixtures.getActorDetail(name = "Leonardo DiCaprio")

        val image = actorDetail.images?.profiles?.maxByOrNull { it.vote_average }

        val expected = ActorDetailDomainModel(
            name = "Leonardo",
            surname = "DiCaprio",
            biography = actorDetail.biography,
            place_of_birth = actorDetail.place_of_birth,
            birthday = actorDetail.birthday,
            deathday = actorDetail.deathday,
            knownFor = null,
            actor_image = "http://image.tmdb.org/t/p/original${image?.file_path}",
            background_image = null
        )

        val result = actorDetail.toDomainModel()

        Assert.assertEquals(result, expected)
    }

}
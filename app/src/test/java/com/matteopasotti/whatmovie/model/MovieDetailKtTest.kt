package com.matteopasotti.whatmovie.model

import com.matteopasotti.whatmovie.DomainFixtures
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailKtTest {


    @Test
    fun `given a movie with multiple genres then type is generated correct`() {

        val movieDetail = DomainFixtures.getMovieDetail(
            genres = mutableListOf(
                Genre(1, "Adventure"),
                Genre(2, "Drama")
            )
        )

        val result = movieDetail.toDomainModel()

        assertEquals(result.type, "Adventure,Drama")
    }

    @Test
    fun `given a movie with only a single genre then type is generated correct`() {

        val movieDetail =
            DomainFixtures.getMovieDetail(genres = mutableListOf(Genre(1, "Adventure")))

        val result = movieDetail.toDomainModel()

        assertEquals(result.type, "Adventure")
    }

    @Test
    fun `given a movie with multiple production countries then productionCountry is generated correct`() {

        val movieDetail = DomainFixtures.getMovieDetail(
            production_countries = mutableListOf(
                ProductionCountry("US", "United States"), ProductionCountry
                    ("IT", "Italy")
            )
        )

        val result = movieDetail.toDomainModel()

        assertEquals(result.productionCountries, "United States,Italy")
    }

    @Test
    fun `given a movie with only a single production country then productionCountry is generated correct`() {

        val movieDetail = DomainFixtures.getMovieDetail(
            production_countries = mutableListOf(
                ProductionCountry("US", "United States")
            )
        )

        val result = movieDetail.toDomainModel()

        assertEquals(result.productionCountries, "United States")
    }

    @Test
    fun `give movie detail we create the correct movie detail domain model`() {
        val movieDetail = DomainFixtures.getMovieDetail(
            genres = mutableListOf(Genre(1, "Adventure")),
            original_language = "en",
            original_title = "title",
            release_date = "date",
            production_countries = mutableListOf(
                ProductionCountry("US", "United States"),
                ProductionCountry("IT", "Italy")
            )
        )

        val result = movieDetail.toDomainModel()

        val expected = MovieDetailDomainModel(
            type = "Adventure",
            language = "en",
            originalTitle = "title",
            overview = "overview",
            productionCountries = "United States,Italy",
            releaseDate = "date",
            vote_average = 2.0F,
            genres = mutableListOf(),
            videos = listOf()
        )

        assertEquals(result, expected)
    }

    @Test
    fun `given movie detail with more than 3 genre, then in movieDetailDomain will we have only the first 3 genre`() {
        val fourthGenre = Genre(4, "Crime")
        val movieDetail = DomainFixtures.getMovieDetail(
            genres = mutableListOf(
                Genre(1, "Horror"),
                Genre(2, "Adventure"),
                Genre(3, "Family"),
                fourthGenre
            )
        )

        val result = movieDetail.toDomainModel()

        assertFalse(result.genres.contains(fourthGenre))
    }
}
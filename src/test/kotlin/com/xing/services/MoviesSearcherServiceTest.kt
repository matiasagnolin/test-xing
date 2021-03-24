package com.xing.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nhaarman.mockitokotlin2.whenever
import com.xing.model.Artist
import com.xing.model.Movie
import com.xing.services.externals.*
import com.xing.services.helpers.ArtistInfoHelper
import com.xing.services.helpers.MovieInfoHelper
import com.xing.services.helpers.MoviesSearchHelper
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.mock.Calls
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest
@RunWith(SpringRunner::class)
class MoviesSearcherServiceTest {

    @Autowired
    private lateinit var movieSearchHelper: MoviesSearchHelper

    @Autowired
    private lateinit var movieInfoHelper: MovieInfoHelper

    @Autowired
    private lateinit var artistInfoHelper: ArtistInfoHelper

    @MockBean
    private lateinit var movieSearchExternalService: MovieSearchExternalService

    @MockBean
    private lateinit var movieInfoExternalService: MovieInfoExternalService

    @MockBean
    private lateinit var artistInfoExternalService: ArtistInfoExternalService

    @Test
    fun `it should retrieve movies id by gender successfully`() {
        //given
        val genre: String = "Drama"
        val offset: Int = 0
        val limit: Int = 10
        val total: Int = 10

        whenever(movieSearchExternalService.getMovieIds(genre, offset, limit)).thenReturn(
            Calls.response(
                MovieSearchResponse(Metadata(offset, limit, total), listOf(1))
            )
        )

        //when
        val response = movieSearchHelper.getMoviesId(genre, offset, limit)?.body()!!

        //then
        assertTrue(response.data.isNotEmpty())
    }

    @Test
    fun `it should retrieve movies details successfully`() {
        //given
        val ids: List<Int> = listOf(1, 2, 3)
        val offset: Int = 0
        val limit: Int = 10
        val total: Int = 10

        val movie = Movie(
            1,
            "Finding Nemo",
            "There are 3.7 trillion fish in the ocean. They're looking for one.",
            "Nemo, an adventurous young clownfish",
            100000000,
            123,
            "1994",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            "",
            "",
            setOf(),
            setOf()
        )

        whenever(movieInfoExternalService.getMovieDetails(ids.joinToString())).thenReturn(
            Calls.response(
                MovieInfoResponse(Metadata(offset, limit, total), listOf(movie))
            )
        )

        //when
        val response = movieInfoHelper.getMovieInfo(ids)?.body()!!

        //then
        assertTrue(response.movies.isNotEmpty())
    }

    @Test
    fun `it should retrieve cast details successfully`() {
        //given
        val ids: List<Int> = listOf(1, 2, 3)
        val offset: Int = 0
        val limit: Int = 10
        val total: Int = 10

        val artist = Artist(1, "name", 2, "", setOf())

        whenever(artistInfoExternalService.getArtistDetails(ids.joinToString())).thenReturn(
            Calls.response(
                ArtistInfoResponse(Metadata(offset, limit, total), listOf(artist))
            )
        )

        //when
        val response = artistInfoHelper.getArtistInfo(ids).body()!!

        //then
        assertTrue(response.artistDetails.isNotEmpty())
    }

    @Test
    fun `it should fully response`() {


    }

    @Test
    fun `it should response without movie details`() {

    }

    @Test
    fun `it should response without casts details`() {

    }

    @Test
    fun `it should retry when fails`() {

    }

    @Test
    fun `it should create error 440`() {

    }

    @Test
    fun `it should create error 450`() {

    }
}
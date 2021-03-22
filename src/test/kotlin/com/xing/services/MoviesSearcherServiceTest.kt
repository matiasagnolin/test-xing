package com.xing.services

import com.nhaarman.mockitokotlin2.whenever
import com.xing.services.externals.Metadata
import com.xing.services.externals.MovieInfoExternalService
import com.xing.services.externals.MovieSearchExternalService
import com.xing.services.externals.MovieSearchResponse
import com.xing.services.helpers.MovieInfoHelper
import com.xing.services.helpers.MoviesSearchHelper
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import retrofit2.Response
import retrofit2.mock.Calls

@SpringBootTest
@RunWith(SpringRunner::class)
class MoviesSearcherServiceTest {

    @Autowired
    private lateinit var movieSearchHelper: MoviesSearchHelper

    @Autowired
    private lateinit var movieInfoHelper: MovieInfoHelper

    @MockBean
    private lateinit var movieSearchExternalService: MovieSearchExternalService

    @MockBean
    private lateinit var movieInfoExternalService: MovieInfoExternalService

    @Test
    fun `it should retrieve movies id by gender successfully`() {
        //given
        val genre: String = "Drama"
        val offset: Int = 0
        val limit: Int = 10
        val total: Int = 10

        whenever(movieSearchExternalService.getMovieIds(genre, limit, offset)).thenReturn(
            Calls.response(
                MovieSearchResponse(Metadata(offset, limit, total), listOf(1))
            )
        )

        //when
        val response = movieSearchHelper.getMoviesId(genre, offset, limit)!!

        //then
        assertTrue(response.isNotEmpty())
    }

    @Test
    fun `it should retrieve movies details successfully`() {
        //given
        val ids: List<Int> = listOf()
        val offset: Int = 0
        val limit: Int = 10
        val total: Int = 10

        whenever(movieInfoExternalService.getMovieDetails(ids, limit, offset)).thenReturn(
            Calls.response(
                MovieDetailsResponse(ids, limit, offset)
            )
        )

        //when
        val response = movieInfoHelper.getMovieInfo(ids, offset, limit)!!

        //then
        assertTrue(response.isNotEmpty())

    }

    @Test
    fun `it should retrieve casts details successfully`() {
        //given
        val genre: String = "Drama"
        val offset: Int = 0
        val limit: Int = 10
        val total: Int = 10

        whenever(movieSearchExternalService.getMovieIds(genre, limit, offset)).thenReturn(
            Calls.response(
                MovieSearchResponse(Metadata(offset, limit, total), listOf(1))
            )
        )

        //when
        val response = movieSearchHelper.getMoviesId(genre, offset, limit)!!

        //then
        assertTrue(response.isNotEmpty())

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
}
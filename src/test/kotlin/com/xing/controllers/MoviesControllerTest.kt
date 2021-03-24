package com.xing.controllers


import com.nhaarman.mockitokotlin2.any
import org.junit.Test
import com.nhaarman.mockitokotlin2.whenever
import com.xing.dto.WrapperDto
import com.xing.services.MoviesSearcherService
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
class MoviesControllerTest {

    @MockBean
    private lateinit var moviesService: MoviesSearcherService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `movies endpoint should return 200`() {
        // given
        val genre = "Drama"
        val offset = 0
        val limit = 10
        whenever(moviesService.getMoviesIdsByGenre(genre, offset, limit)).thenReturn(null)

        // when
        mockMvc.get("/movies?genre=$genre&offset=$offset&limit=$limit") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            // then
            status { isOk() }
        }
    }
}
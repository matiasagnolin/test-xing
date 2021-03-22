package com.xing.services.helpers

import com.xing.model.Movie
import com.xing.services.externals.MovieInfoExternalService
import com.xing.services.externals.MovieSearchResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MovieInfoHelper(@Value("\${services.paths.microservice-delay}") private val connectionTimeout: Long) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    @Qualifier("movieInfoService")
    private lateinit var movieInfoExternalService: MovieInfoExternalService

    fun getMovieInfo(ids: List<Int>,offset: Int, limit: Int): List<Movie>? {


        return null
    }


}
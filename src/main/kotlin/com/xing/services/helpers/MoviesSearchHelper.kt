package com.xing.services.helpers

import com.xing.services.externals.MovieSearchResponse
import com.xing.services.externals.MovieSearchExternalService
import com.xing.services.helpers.utils.decorate
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class MoviesSearchHelper {

    private val log = LoggerFactory.getLogger(javaClass)
    private val delayThreshold: Long = 200

    @Autowired
    @Qualifier("movieSearchService")
    private lateinit var movieSearchExternalService: MovieSearchExternalService

    private val circuitBreaker = CircuitBreaker.ofDefaults("MovieSearch")

    fun getMoviesId(genre: String, offset: Int, limit: Int): List<Int>? {
        try {
            log.info("START calling to Movie-Search ms")

            val start = System.currentTimeMillis()

            val response = movieSearchExternalService
                .getMovieIds(genre, limit, offset)
                .decorate(circuitBreaker).call()

            (System.currentTimeMillis() - start).let { delay ->
                if (delay > delayThreshold) log.warn("[getMoviesId] Call took: ${delay}ms (delay threshold: ${delayThreshold}ms)")
            }

            if (response.isSuccessful && response.body() != null) {
                log.info("Call to getMoviesId was successful")

                return (response.body() as MovieSearchResponse).moviesIds
            }

        } catch (e: Exception) {
            log.error(
                "[getMoviesId] Error calling external Referrals MS; request: genre: $genre, limit: $limit, offset: $offset",
                e
            )
        }

        return null
    }
}
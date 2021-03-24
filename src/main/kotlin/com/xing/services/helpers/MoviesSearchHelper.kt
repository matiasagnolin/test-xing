package com.xing.services.helpers

import com.xing.services.externals.MovieSearchExternalService
import com.xing.services.externals.MovieSearchResponse
import com.xing.services.helpers.utils.decorate
import io.github.resilience4j.circuitbreaker.CircuitBreaker

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value

import org.springframework.stereotype.Component
import retrofit2.Response
import java.io.InterruptedIOException


@Component
class MoviesSearchHelper(
    @Value("\${services.paths.microservice-delay}")
    private val delayThreshold: Long,
    @Value("\${services.paths.microservice-retry}")
    private val countRetry: Int,
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val msName = "MOVIE-SEARCH"

    @Autowired
    @Qualifier("movieSearchService")
    private lateinit var movieSearchExternalService: MovieSearchExternalService

    @Autowired
    private lateinit var evaluateResponse: ResponseHandler<MovieSearchResponse>

    private val circuitBreaker = CircuitBreaker.ofDefaults("MovieInfo")

    init {
        circuitBreaker.eventPublisher.onStateTransition {
            log.warn("$msName circuit breaker transitioned: $it")
        }.onError {
            log.error("Error on $msName circuit breaker: $it")
        }
    }

    fun getMoviesId(genre: String, offset: Int, limit: Int): Response<MovieSearchResponse>? {
        log.info("START calling to Movie-Search ms")
        var i = 0

        var response = makeTheCall(genre, offset, limit)

        var retry = evaluateResponse.responseHandler(response, msName, null)


        while (!retry and (i < countRetry)) {
            response = makeTheCall(genre, offset, limit)
            retry = evaluateResponse.responseHandler(response, msName, null)
            i++
        }

        return response
    }

    private fun makeTheCall(genre: String, offset: Int, limit: Int): Response<MovieSearchResponse>? {
        return try {
            val start = System.currentTimeMillis()
            val response = movieSearchExternalService
                .getMovieIds(genre, offset, limit)
                .decorate(circuitBreaker).call()
            (System.currentTimeMillis() - start).let { delay ->
                if (delay > delayThreshold) log.warn("[getMoviesId] Call took: ${delay}ms (delay threshold: ${delayThreshold}ms)")
            }
            response
        } catch (e: InterruptedIOException) {
            log.error("Call was interrupted!!")
            null
        }
    }
}
package com.xing.services.helpers


import com.xing.services.externals.MovieInfoExternalService
import com.xing.services.externals.MovieInfoResponse
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
class MovieInfoHelper(
    @Value("\${services.paths.microservice-delay}")
    private val delayThreshold: Long,
    @Value("\${services.paths.microservice-retry}")
    private val countRetry: Int,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    private val circuitBreaker = CircuitBreaker.ofDefaults("MovieInfo")

    private val msName = "MOVIE-INFO"


    @Autowired
    private lateinit var evaluateResponse: ResponseHandler<MovieInfoResponse>

    @Autowired
    @Qualifier("movieInfoService")
    private lateinit var movieInfoExternalService: MovieInfoExternalService


    fun getMovieInfo(ids: List<Int>): Response<MovieInfoResponse>? {
        var i = 0
        var response = makeTheCall(ids)

        var retry = evaluateResponse.responseHandler(response, msName, null)

        while (!retry and (i < countRetry)) {
            response = makeTheCall(ids)
            retry = evaluateResponse.responseHandler(response, msName, null)
            i++
        }

        return response
    }

    private fun makeTheCall(genre: List<Int>): Response<MovieInfoResponse>? {
        return try {
            val start = System.currentTimeMillis()
            val response = movieInfoExternalService
                .getMovieDetails(genre.joinToString())
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
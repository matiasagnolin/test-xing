package com.xing.services.helpers

import com.xing.services.externals.ArtistInfoExternalService
import com.xing.services.externals.ArtistInfoResponse
import com.xing.services.helpers.utils.decorate
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import retrofit2.Response

@Component
class ArtistInfoHelper(
    @Value("\${services.paths.microservice-delay}")
    private val delayThreshold: Long,
    @Value("\${services.paths.microservice-retry}")
    private val countRetry: Int,
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val msName = "ARTIST-INFO"

    @Autowired
    private lateinit var evaluateResponse: ResponseHandler<ArtistInfoResponse>

    @Autowired
    @Qualifier("artistInfoService")
    private lateinit var artistInfoExternalService: ArtistInfoExternalService

    private val circuitBreaker = CircuitBreaker.ofDefaults(msName)


    init {
        circuitBreaker.eventPublisher.onStateTransition {
            log.warn("$msName circuit breaker transitioned: $it")
        }.onError {
            log.error("Error on $msName circuit breaker: $it")
        }
    }

    fun getArtistInfo(ids: List<Int>): Response<ArtistInfoResponse> {

        log.info("START calling to Artist-Info ms")
        var i = 0
        var response = makeTheCall(ids)
        var retry = evaluateResponse.responseHandler(response, msName, null)

        while (!retry and (i < countRetry)) {
            response = makeTheCall(ids)
            retry = evaluateResponse.responseHandler(response, msName, null)
        }
        return response
    }

    private fun makeTheCall(ids: List<Int>): Response<ArtistInfoResponse> {
        val start = System.currentTimeMillis()
        val response = artistInfoExternalService
            .getArtistDetails(ids.joinToString())
            .decorate(circuitBreaker).call()
        (System.currentTimeMillis() - start).let { delay ->
            if (delay > delayThreshold) log.warn("[getMoviesId] Call took: ${delay}ms (delay threshold: ${delayThreshold}ms)")
        }
        return response
    }
}
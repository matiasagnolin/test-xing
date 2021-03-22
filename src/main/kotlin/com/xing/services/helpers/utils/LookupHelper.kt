package com.xing.services.helpers.utils

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Callable

fun <T> Call<T>.decorate(circuitBreaker: CircuitBreaker): Callable<Response<T>> =
    CircuitBreaker.decorateCallable(circuitBreaker, this::execute)

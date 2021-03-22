package com.xing.services.externals

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInfoExternalService {

    @GET("/movies")
    fun getMovieDetails(
        @Query("ids") origin: List<Int>,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<MovieSearchResponse>

}
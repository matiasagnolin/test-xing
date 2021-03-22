package com.xing.services.externals

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistInfoExternalService {

    @GET("/artists")
    fun getMovieIds(
        @Query("ids") origin: List<Int>,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<MovieSearchResponse>
}
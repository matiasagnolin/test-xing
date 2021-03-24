package com.xing.services.externals

import com.fasterxml.jackson.annotation.JsonProperty
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchExternalService {

    @GET("/movies")
    fun getMovieIds(
        @Query("genre") origin: String,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): Call<MovieSearchResponse>

}

data class Metadata(val offset: Int?, val limit: Int?, val total: Int?)


data class MovieSearchResponse(

    @JsonProperty("metadata")
    val metadata: Metadata?,

    @JsonProperty("data")
    var data: List<Int>
)
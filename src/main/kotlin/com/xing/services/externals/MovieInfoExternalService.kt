package com.xing.services.externals

import com.fasterxml.jackson.annotation.JsonProperty
import com.xing.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInfoExternalService {

    @GET("/movies")
    fun getMovieDetails(
        @Query("ids") origin: String
    ): Call<MovieInfoResponse>
}

data class MovieInfoResponse(

    @JsonProperty("metadata")
    val metadata: Metadata,

    @JsonProperty("data")
    var movies: List<Movie>
)
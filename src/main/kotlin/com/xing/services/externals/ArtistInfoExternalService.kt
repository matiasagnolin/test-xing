package com.xing.services.externals

import com.fasterxml.jackson.annotation.JsonProperty
import com.xing.model.Artist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistInfoExternalService {

    @GET("/artists")
    fun getArtistDetails(
        @Query("ids") origin: String
    ): Call<ArtistInfoResponse>
}

data class ArtistInfoResponse(

    @JsonProperty("metadata")
    val metadata: Metadata,

    @JsonProperty("data")
    val artistDetails: List<Artist>
)
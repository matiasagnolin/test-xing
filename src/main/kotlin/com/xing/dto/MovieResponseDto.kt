package com.xing.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.xing.model.Artist
import com.xing.model.Genre
import com.xing.services.externals.Metadata
import com.xing.services.helpers.utils.Error

data class MovieResponseDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("title")
    val title: String?,

    @JsonProperty("releaseYear")
    val releaseDate: Int?,

    @JsonProperty("revenue")
    val revenue: String?,

    @JsonProperty("posterPath")
    val posterPath: String?,

    @JsonProperty("genres")
    var genres: List<String>? = null,

    @JsonProperty("cast")
    var artists: List<Artist>? = null,

    )

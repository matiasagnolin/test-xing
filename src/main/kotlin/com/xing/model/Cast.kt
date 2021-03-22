package com.xing.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Cast(
    @JsonProperty("id")
    val id: Int = 0,

    @JsonProperty("name")
    val name: String?,

    @JsonProperty("gender")
    val gender: Int?,

    @JsonProperty("profilePath")
    val profilePath: String?,

    @JsonProperty("movies")
    val movies: Set<Movie>?,
)

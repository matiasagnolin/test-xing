package com.xing.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Genre(
    @JsonProperty("id")
    val id: Int = 0,

    @JsonProperty("name")
    val genreName: String?,
)
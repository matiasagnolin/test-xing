package com.xing.dto

import com.fasterxml.jackson.annotation.JsonProperty


data class ResponseDto(
    @JsonProperty("movies")
    val movies: List<MovieResponseDto>,
)

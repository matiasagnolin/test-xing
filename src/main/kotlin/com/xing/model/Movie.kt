package com.xing.model


import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

data class Movie(
    @JsonProperty("id")
    val id: Int = 0,

    @JsonProperty("title")
    val title: String?,

    @JsonProperty("tagline")
    val tagline: String?,

    @JsonProperty("overview")
    val overview: String?,

    @JsonProperty("popularity")
    val popularity: Int,

    @JsonProperty("runtime")
    val runtime: Int?,

    @JsonProperty("releaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var releaseDate: String?,

    @JsonProperty("revenue")
    var revenue: BigDecimal?,

    @JsonProperty("budget")
    var budget: BigDecimal?,

    @JsonProperty("posterPath")
    val posterPath: String?,

    @JsonProperty("originalLanguage")
    val originalLanguage: String?,

    @JsonProperty("genres")
    val genres: Set<Int>,

    @JsonProperty("cast")
    val artists: Set<Int>,

    )
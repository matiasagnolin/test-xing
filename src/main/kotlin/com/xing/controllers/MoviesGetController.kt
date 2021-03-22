package com.xing.controllers

import com.xing.services.MoviesSearcherService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MoviesGetController {

    @Autowired
    private lateinit var moviesService: MoviesSearcherService

    @ApiOperation(value = "List movies details by genre")
    @GetMapping("/movies")
    fun getMoviesByGenre(
        @RequestParam("genre", required = true) genre: String,
        @RequestParam("offset", required = false) offset: Int?,
        @RequestParam("limit", required = false) limit: Int?
    ): ResponseEntity<Any> {
        return ResponseEntity(moviesService.getMoviesIdsByGenre(genre, offset ?: 0, limit ?: 10), HttpStatus.OK)
    }
}
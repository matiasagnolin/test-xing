package com.xing.services

import com.xing.dto.ResponseDto
import com.xing.dto.WrapperDto
import com.xing.mapper.MovieResponseMapper
import com.xing.model.Artist
import com.xing.model.Movie
import com.xing.services.helpers.ArtistInfoHelper
import com.xing.services.helpers.MovieInfoHelper
import com.xing.services.helpers.MoviesSearchHelper
import com.xing.services.helpers.utils.ErrorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MoviesSearcherService {

    @Autowired
    private lateinit var moviesSearchHelper: MoviesSearchHelper

    @Autowired
    private lateinit var movieInfoHelper: MovieInfoHelper

    @Autowired
    private lateinit var artistInfoHelper: ArtistInfoHelper

    private val limitParameterPerRequest = 5

    fun getMoviesIdsByGenre(genre: String, offset: Int, limit: Int): WrapperDto? {

        var moviesDetails: MutableList<List<Movie>> = mutableListOf()
        var artistsList: MutableList<List<Artist>> = mutableListOf()


        val moviesIds = moviesSearchHelper.getMoviesId(genre, offset, limit)?.body()?.data
        val metadata = moviesSearchHelper.getMoviesId(genre, offset, limit)?.body()?.metadata!!

        if (!moviesIds.isNullOrEmpty()) {

            val parametersList = splitCallsWhenExceededParameters(moviesIds, limitParameterPerRequest)

            for (paramMovies in parametersList) {
                movieInfoHelper.getMovieInfo(paramMovies as List<Int>)?.body()?.movies?.let { moviesDetails.add(it) }
            }

            val artistsIds = moviesDetails.flatMap { it.flatMap { it.artists } }

            val parametersArtistList = splitCallsWhenExceededParameters(artistsIds, limitParameterPerRequest)

            for (paramArtists in parametersArtistList) {
                artistInfoHelper.getArtistInfo(paramArtists as List<Int>)?.body()?.artistDetails?.let {
                    artistsList.add(
                        it
                    )
                }
            }
            val result =
                MovieResponseMapper(
                    moviesDetails.flatten(),
                    artistsList.flatten().map { it.id to it }.toMap(),
                    metadata
                )

            return WrapperDto(ResponseDto(result.map()),metadata, ErrorHandler.errorList)
        }
        return null
    }


    private fun splitCallsWhenExceededParameters(list: List<Any>, dividedBy: Int): List<List<Any>> {
        return list.withIndex()
            .groupBy { it.index / dividedBy }
            .map { it.value.map { it.value } }
    }


}
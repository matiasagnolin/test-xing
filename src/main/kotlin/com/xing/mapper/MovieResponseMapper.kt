package com.xing.mapper

import com.xing.dto.MovieResponseDto
import com.xing.model.Artist
import com.xing.model.Genre
import com.xing.model.Movie
import com.xing.services.externals.Metadata
import java.text.NumberFormat
import java.util.*

data class MovieResponseMapper(
    val movies: List<Movie>,
    val artistsMap: Map<Int, Artist>,
    val metadata: Metadata
) {

    var responseList: List<MovieResponseDto> = mutableListOf()

    fun map(): List<MovieResponseDto> {
        return movies.map { createDto(it) }
    }

    private fun createDto(movie: Movie): MovieResponseDto {

        var dto = MovieResponseDto(
            id = movie.id.toString(),
            title = movie.title,
            releaseDate = movie.releaseDate?.substring(0,4)?.toInt(),
            revenue = "US$ " +
                NumberFormat.getInstance(Locale.US).format(movie.revenue).toString(),
            posterPath = movie.posterPath,
        )

        var artistsList: MutableList<Artist> = mutableListOf()
        var genreList: MutableList<Genre> = mutableListOf()

        for (artist in movie.artists) {
            val foundArtist = artistsMap[artist]
            if (foundArtist != null) {
                artistsList.add(foundArtist)
            }
        }

/*        for (genre in movie.genres) {
            val foundGenre = genresMap[genre.toString()]
            if (foundGenre != null) {
                genreList.add(foundGenre)
            }
        }*/

        dto.artists = artistsList
        dto.genres = genreList

        return dto
    }
}
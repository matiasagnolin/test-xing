package com.xing.services

import com.xing.services.helpers.MoviesSearchHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MoviesSearcherService {

    @Autowired
    private lateinit var moviesSearchHelper: MoviesSearchHelper

    fun getMoviesIdsByGenre(genre: String, offset: Int, limit: Int): List<Int>? {
        return moviesSearchHelper.getMoviesId(genre, offset, limit)
    }

}
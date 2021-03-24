package com.xing.services.helpers.utils

object ErrorHandler {

    var errorList: MutableList<Error> = mutableListOf()
    private val errorMessageMap: Map<Int, String> =
        mapOf(440 to "cast info is not complete", 450 to "details can not be retrieved")
    private val MS_NAMES = setOf("MOVIE-INFO","ARTIST-INFO")

    fun logError(movieId: Int?, msName: String?, errorCode: Int) {
        if(MS_NAMES.contains(msName))
        errorList.add(
            Error(errorCode, "Movie id $movieId" + errorMessageMap[errorCode]))
    }

}


data class Error(val errorCode: Int, val errorMessage: String?)
package com.xing.services.helpers.utils

object ErrorHandler {

    var errorList: MutableList<Error> = mutableListOf()
    private val errorMessageMap: Map<String, String> =
        mapOf("ARTIST-INFO" to "cast info is not complete", "MOVIE-INFO" to "details can not be retrieved")
    private val errorCodeMap: Map<String, Int> =
        mapOf("ARTIST-INFO" to 440, "MOVIE-INFO" to 450)
    private val MS_NAMES = setOf("MOVIE-INFO","ARTIST-INFO")

    fun logError(movieId: Int?, msName: String?) {
        if(MS_NAMES.contains(msName)) {

            errorList.add(
                Error(errorCodeMap[msName], "Movie id " + errorMessageMap[msName])
            )
        }
    }

}


data class Error(val errorCode: Int?, val errorMessage: String?)
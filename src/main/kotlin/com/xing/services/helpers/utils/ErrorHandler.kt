package com.xing.services.helpers

object ErrorHandler {

    private lateinit var errorList: MutableList<Error>

    fun logError(movieId: Int, errorType: String, errorCode: Int, finalMessage: String) {
        val message = "Movie id $movieId $errorType $finalMessage"
        errorList.add(Error(errorCode,message))
    }

}


data class Error(val errorCode: Int, val errorMessage: String)
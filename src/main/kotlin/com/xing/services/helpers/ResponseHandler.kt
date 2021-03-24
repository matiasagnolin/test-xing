package com.xing.services.helpers


import com.xing.services.helpers.utils.ErrorHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ResponseHandler<T> {

    private val log = LoggerFactory.getLogger(javaClass)

    fun responseHandler(response: retrofit2.Response<T>?, callType: String?, movieId: Int?): Boolean {
        if (response == null) {
            return false
        }
        return if (response.isSuccessful) {
            log.info("Call was successful!!")
            true
        } else {
            log.error("[$callType] Error calling external MS)")
            ErrorHandler.logError(movieId, callType)
            false
        }
    }
}
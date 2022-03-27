package com.yago.gas.data.api

import retrofit2.Response
import java.util.regex.Pattern

@Suppress("unused")
sealed class ApiResponse<T> {
    companion object {

        fun <T> create(error: Throwable): ApiComunicationError<T> {
            return ApiComunicationError(408, error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse(204)
                } else {
                    ApiSuccessResponse(
                        body = body,
                        linkHeader = response.headers()?.get("link")
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                if (response.code() == 401) {
                    ApiRefreshTokenError(response.code(), response.message())
                } else {

                    ApiErrorResponse(
                        response.code(),
                        response.message(),
                        errorMsg ?: "unknown error"
                    )
                }
            }
        }
    }
}


data class ApiSuccessResponse<T>(
    val body: T,
    val links: Map<String, String>
) : ApiResponse<T>() {
    constructor(body: T, linkHeader: String?) : this(
        body = body,
        links = linkHeader?.extractLinks() ?: emptyMap()
    )

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }
    }
}

class ApiEmptyResponse<T>(val code: Int) : ApiResponse<T>()

data class ApiErrorResponse<T>(val code: Int, val message: String, val fullMessage: String) :
    ApiResponse<T>()

data class ApiComunicationError<T>(val code: Int, val message: String) : ApiResponse<T>()

data class ApiRefreshTokenError<T>(val code: Int, val message: String) : ApiResponse<T>()

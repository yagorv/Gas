package com.yago.gas.data.utils

data class Resource<out T>(val status: Status, val data: T?, val code: Int?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(code: Int, msg: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, code, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null, null)
        }

        fun <T> fastload(data: T?): Resource<T> {
            return Resource(Status.FASTLOAD, data, null, null)
        }

        fun <T> refreshToken(data: T?): Resource<T> {
            return Resource(Status.REFRESH_TOKEN, data, null, null)
        }
    }
}

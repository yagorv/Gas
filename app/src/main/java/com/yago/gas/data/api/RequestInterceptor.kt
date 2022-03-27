package com.yago.gas.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class RequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().build()
        return chain.proceed(request)
    }

}
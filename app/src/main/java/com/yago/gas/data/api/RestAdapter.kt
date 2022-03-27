package com.yago.gas.data.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.yago.gas.domain.livedata.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RestAdapter @Inject constructor(
    private val requestInterceptor: RequestInterceptor
) {

    fun getGasBackendApi(): GasBackendApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
                        .create()
                )
            )
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(
                OkHttpClient.Builder().addInterceptor(requestInterceptor)
                    .readTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(GasBackendApi::class.java)
    }

    companion object {
        const val BASE_URL = "http://prod.klikin.com/"
    }

}
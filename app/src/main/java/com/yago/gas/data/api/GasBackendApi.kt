package com.yago.gas.data.api

import androidx.lifecycle.LiveData
import com.yago.gas.data.api.response.StoreResponse
import retrofit2.http.GET

interface GasBackendApi {

    @GET("commerces/public")
    fun getStores(): LiveData<ApiResponse<List<StoreResponse>>>

}
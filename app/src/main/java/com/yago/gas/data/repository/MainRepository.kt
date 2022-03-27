package com.yago.gas.data.repository

import androidx.lifecycle.LiveData
import com.yago.gas.data.api.ApiResponse
import com.yago.gas.data.api.ApiSuccessResponse
import com.yago.gas.data.api.GasBackendApi
import com.yago.gas.data.api.response.StoreResponse
import com.yago.gas.data.api.response.UserTokenResponse
import com.yago.gas.data.utils.ApiNetworkBoundResource
import com.yago.gas.data.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val gasBackendApi: GasBackendApi
) : ApiRepository() {

    fun getActiveStores(): LiveData<Resource<List<StoreResponse>>> {
        return object : ApiNetworkBoundResource<List<StoreResponse>, List<StoreResponse>>() {

            override fun processTryToRefreshResponse(userTokenResponse: ApiResponse<UserTokenResponse>?) =
                processRefreshToken()

            override fun refreshTokenCall() = tryToRefreshToken()

            override fun processResponse(response: ApiSuccessResponse<List<StoreResponse>>): List<StoreResponse> {
                return response.body.filter { it.active == true }
            }

            override fun createCall() = gasBackendApi.getStores()

        }.asLiveData()
    }

}





package com.yago.gas.data.repository

import androidx.lifecycle.LiveData
import com.yago.gas.data.api.ApiResponse
import com.yago.gas.data.api.response.UserTokenResponse
import com.yago.gas.domain.livedata.AbsentLiveData

abstract class ApiRepository {

    protected fun tryToRefreshToken(): LiveData<ApiResponse<UserTokenResponse>> {
        //TO-COMPLETE
        return AbsentLiveData.create()
    }

    protected fun processRefreshToken(): Boolean {
        //TO-COMPLETE
        return true
    }

}
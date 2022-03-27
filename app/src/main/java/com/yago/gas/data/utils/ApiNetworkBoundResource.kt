package com.yago.gas.data.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.yago.gas.data.api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class ApiNetworkBoundResource<ResultType, RequestType>
@MainThread constructor() : ApiNetwork() {

    private val result = MediatorLiveData<Resource<ResultType>>()

    private val job = Job()
    private val coroutineMainScope: CoroutineScope = CoroutineScope(job + Dispatchers.Main)

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    coroutineMainScope.launch {
                        setValue(Resource.success(processResponse(response)))
                    }
                }
                is ApiEmptyResponse -> {
                    setValue(Resource.success(null))
                }
                is ApiErrorResponse -> {
                    setValue(
                        Resource.error(
                            response.code,
                            response.message,
                            null
                        )
                    )
                }
                is ApiComunicationError -> {
                    setValue(Resource.error(response.code, response.message, null))
                }
                else -> {
                    setValue(Resource.success(null))
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected abstract fun processResponse(response: ApiSuccessResponse<RequestType>): ResultType

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

}

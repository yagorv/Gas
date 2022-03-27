package com.yago.gas.domain.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.yago.gas.data.api.response.StoreResponse
import com.yago.gas.data.repository.MainRepository
import com.yago.gas.data.utils.Resource
import com.yago.gas.domain.livedata.AbsentLiveData
import javax.inject.Inject

class MainInteractor @Inject constructor(private val mainRepository: MainRepository) {

    private val storesTrigger = MutableLiveData<Unit>()

    val stores: LiveData<Resource<List<StoreResponse>>> = Transformations
        .switchMap(storesTrigger) { filter ->
            if (filter != null) {
                mainRepository.getActiveStores()
            } else {
                AbsentLiveData.create()
            }
        }

    fun getStores() {
        storesTrigger.value = Unit
    }

}
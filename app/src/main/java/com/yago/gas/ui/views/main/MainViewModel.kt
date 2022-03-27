package com.yago.gas.ui.views.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yago.gas.data.api.response.StoreResponse
import com.yago.gas.data.utils.Resource
import com.yago.gas.data.utils.Status
import com.yago.gas.domain.customdata.Store
import com.yago.gas.domain.customdata.StoreCategory
import com.yago.gas.domain.interactor.MainInteractor
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainInteractor: MainInteractor) : ViewModel() {

    private lateinit var location: Location
    val _isLoading = MutableLiveData<Boolean>()

    val stores: LiveData<Resource<List<Store>>> = Transformations
        .switchMap(mainInteractor.stores) { stores ->
            val result = MutableLiveData<Resource<List<Store>>>()
            when (stores.status) {
                Status.ERROR -> {
                    result.postValue(Resource.error(400, "errorMessage", null))
                    result
                }
                Status.LOADING -> {
                    result.postValue(Resource.loading(null))
                    result
                }
                Status.FASTLOAD -> {
                    result.postValue(Resource.fastload(emptyList()))
                    result
                }
                else -> {
                    val orderedStores =
                        stores.data?.map { it.toStoreDomain(location) }?.sortedBy { it.distance }
                    result.postValue(Resource.success(orderedStores))
                    result
                }
            }
        }

    val isLoading: LiveData<Boolean> = Transformations
        .switchMap(_isLoading) {
            val result = MutableLiveData<Boolean>()
            result.postValue(it)
            result

        }

    fun onCreateMainScreen(location: Location) {
        this.location = location
        mainInteractor.getStores()
    }

    fun onViewCreated() {
        onNavigationBackStack(false)
    }

    fun onNavigationBackStack(value: Boolean = true) {
        _isLoading.value = value
    }

}

fun StoreResponse?.toStoreDomain(location: Location): Store =
    Store(
        this?.slug ?: "",
        this?.name ?: "",
        this?.shortDescription ?: "",
        this?.description ?: "",
        StoreCategory.getFromValue(this?.category),
        this?.logo?.url ?: "",
        createLocation(this?.latitude, this?.longitude),
        this?.address?.street ?: "",
        this?.address?.country ?: "",
        this?.address?.city ?: "",
        this?.openingHours ?: "",
        location.getDistanceTo(this?.latitude, this?.longitude)
    )


fun createLocation(latitude: Double?, longitude: Double?): Location =
    Location("").apply {
        this.latitude = latitude ?: 0.0
        this.longitude = longitude ?: 0.0
    }

fun Location.getDistanceTo(latitude: Double?, longitude: Double?): Int {
    val loc = Location("").apply {
        this.latitude = latitude ?: 0.0
        this.longitude = longitude ?: 0.0
    }
    return this.distanceTo(loc).toInt()
}

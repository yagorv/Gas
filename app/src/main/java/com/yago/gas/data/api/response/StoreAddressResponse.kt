package com.yago.gas.data.api.response

import com.google.gson.annotations.SerializedName

data class StoreAddressResponse(

    @SerializedName("street")
    val street: String?,

    @SerializedName("country")
    val country: String?,

    @SerializedName("city")
    val city: String?

)
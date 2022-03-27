package com.yago.gas.data.api.response

import com.google.gson.annotations.SerializedName

data class StoreResponse(

    @SerializedName("slug")
    val slug: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("shortDescription")
    val shortDescription: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("category")
    val category: String?,

    @SerializedName("logo")
    val logo: StoreLogoResponse?,

    @SerializedName("latitude")
    val latitude: Double?,

    @SerializedName("longitude")
    val longitude: Double?,

    @SerializedName("active")
    val active: Boolean?,

    @SerializedName("address")
    val address: StoreAddressResponse?,

    @SerializedName("openingHours")
    val openingHours: String?

)
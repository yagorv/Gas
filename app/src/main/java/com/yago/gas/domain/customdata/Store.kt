package com.yago.gas.domain.customdata

import android.location.Location
import java.io.Serializable

data class Store(

    val slug: String,

    val name: String,

    val shortDescription: String,

    val description: String,

    val category: StoreCategory,

    val imageUrl: String,

    val location: Location,

    val street: String,

    val country: String,

    val city: String,

    val openingHours: String,

    val distance: Int

) : Serializable
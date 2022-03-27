package com.yago.gas.data.api.response

import com.google.gson.annotations.SerializedName

data class UserTokenResponse(

    @SerializedName("access_token")
    val token: String,

    @SerializedName("refresh_token")
    val refreshToken: String

)
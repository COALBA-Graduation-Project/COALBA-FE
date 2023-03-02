package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class GoogleLoginResponseData(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)

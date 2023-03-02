package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class ProfileLookResponseData(
    @SerializedName("realName")
    val realName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)

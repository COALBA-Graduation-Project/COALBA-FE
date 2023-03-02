package com.example.coalba.data.request

import com.google.gson.annotations.SerializedName

data class ProfileEditData(
    @SerializedName("profile")
    val profile: EditData?
)
data class EditData(
    @SerializedName("realName")
    val realName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("prevImageUrl")
    val prevImageUrl: String
)

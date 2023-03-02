package com.example.coalba.data.request

import com.google.gson.annotations.SerializedName

data class ProfileRegisterData(
    @SerializedName("profile")
    val profile: RegisterData?
)
data class RegisterData(
    @SerializedName("realName")
    val realName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("birthDate")
    val birthDate: String
)
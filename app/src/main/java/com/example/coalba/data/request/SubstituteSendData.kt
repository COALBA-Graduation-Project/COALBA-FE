package com.example.coalba.data.request

import com.google.gson.annotations.SerializedName

data class SubstituteSendData(
    @SerializedName("reqMessage")
    val reqMessage: String
)

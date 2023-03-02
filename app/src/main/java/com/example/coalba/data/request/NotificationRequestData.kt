package com.example.coalba.data.request

import com.google.gson.annotations.SerializedName

data class NotificationRequestData(
    @SerializedName("deviceToken")
    val deviceToken: String
)

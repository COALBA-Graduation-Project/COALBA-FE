package com.example.coalba.data.request

import com.google.gson.annotations.SerializedName

data class MessageSendData(
    @SerializedName("content")
    val content: String
)

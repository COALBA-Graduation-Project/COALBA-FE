package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleStartResponseData(
    @SerializedName("scheduleId")
    val scheduleId: Long,
    @SerializedName("logicalStartTime")
    val logicalStartTime: String,
    @SerializedName("status")
    val status: String
)

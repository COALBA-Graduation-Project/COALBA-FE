package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleEndResponseData(
    @SerializedName("scheduleId")
    val scheduleId: Long,
    @SerializedName("logicalEndTime")
    val logicalEndTime: String,
    @SerializedName("status")
    val status: String
)

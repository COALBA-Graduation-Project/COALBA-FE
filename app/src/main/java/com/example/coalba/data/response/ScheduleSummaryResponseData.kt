package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleSummaryResponseData(
    @SerializedName("scheduleId")
    val scheduleId: Long,
    @SerializedName("scheduleDate")
    val scheduleDate: String,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime: String,
    @SerializedName("scheduleEndTime")
    val scheduleEndTime: String,
    @SerializedName("workspaceName")
    val workspaceName: String
)

package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleEachWorkspaceScheduleResponseData(
    @SerializedName("selectedDay")
    val selectedDay: Int,
    @SerializedName("selectedScheduleList")
    var selectedScheduleList: List<ScheduleEachDateListData> = arrayListOf()
)
data class ScheduleEachDateListData(
    @SerializedName("scheduleId")
    val scheduleId: Long,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime: String,
    @SerializedName("scheduleEndTime")
    val scheduleEndTime: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("worker")
    val worker: DateWorkerData?,
    @SerializedName("isMySchedule")
    val isMySchedule: Boolean
)
data class DateWorkerData(
    @SerializedName("workerId")
    val workerId: Long,
    @SerializedName("name")
    val name: String
)

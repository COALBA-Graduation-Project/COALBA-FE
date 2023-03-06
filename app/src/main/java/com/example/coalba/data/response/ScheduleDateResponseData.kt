package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleDateResponseData(
    @SerializedName("selectedDate")
    val selectedDate: SelectedEachDateData?,
    @SerializedName("selectedScheduleList")
    var selectedScheduleList: List<SelectedEachScheduleListData> = arrayListOf()
)
data class SelectedEachDateData(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("day")
    val day: Int,
    @SerializedName("dayOfWeek")
    val dayOfWeek: String
)
data class SelectedEachScheduleListData(
    @SerializedName("scheduleId")
    val scheduleId: Long,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime: String,
    @SerializedName("scheduleEndTime")
    val scheduleEndTime: String,
    @SerializedName("logicalStartTime")
    val logicalStartTime: String,
    @SerializedName("logicalEndTime")
    val logicalEndTime: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("workspace")
    val workspace: WorkspaceScheduleDateData?
)
data class WorkspaceScheduleDateData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("name")
    val name: String
)

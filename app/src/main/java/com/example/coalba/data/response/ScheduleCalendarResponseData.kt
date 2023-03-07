package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleCalendarResponseData(
    @SerializedName("selectedWorkspace")
    val selectedWorkspace: SelectedWorkspaceData?,
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("dateList")
    var dateList: List<WorkspaceDateListData> = arrayListOf(),
    @SerializedName("selectedSubPage")
    val selectedSubPage: SelectedScheduleListOfDayData?
)
data class SelectedWorkspaceData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)
data class WorkspaceDateListData(
    @SerializedName("day")
    val day: Int,
    @SerializedName("dayOfWeek")
    val dayOfWeek: String,
    @SerializedName("isMySchedule")
    val isMySchedule: Boolean
)
data class SelectedScheduleListOfDayData(
    @SerializedName("selectedDay")
    val selectedDay: Int,
    @SerializedName("selectedScheduleList")
    var selectedScheduleList: List<SelectedScheduleWorkspaceListData> = arrayListOf(),
)
data class SelectedScheduleWorkspaceListData(
    @SerializedName("scheduleId")
    val scheduleId: Long,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime: String,
    @SerializedName("scheduleEndTime")
    val scheduleEndTime: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("worker")
    val worker: SchedhuleWorkerData?,
    @SerializedName("isMySchedule")
    val isMySchedule: Boolean
)
data class SchedhuleWorkerData(
    @SerializedName("workerId")
    val workerId: Long,
    @SerializedName("name")
    val name: String
)

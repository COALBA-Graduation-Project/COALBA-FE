package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class WorkHistoryListResponseData(
    @SerializedName("selectedYear")
    val selectedYear: Int,
    @SerializedName("workReportList")
    var workReportList: List<WorkHistoryListData> = arrayListOf()
)
data class WorkHistoryListData(
    @SerializedName("month")
    val month: Int?,
    @SerializedName("totalWorkTimeHour")
    val totalWorkTimeHour: Int,
    @SerializedName("totalWorkTimeMin")
    val totalWorkTimeMin: Int,
    @SerializedName("totalWorkPay")
    val totalWorkPay: String
)

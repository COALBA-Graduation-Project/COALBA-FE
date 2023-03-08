package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class SubstituteToResponseData(
    @SerializedName("totalSubstituteReqList")
    var totalSubstituteReqList: List<TotalSubstituteReqToListData> = arrayListOf()
)
data class TotalSubstituteReqToListData(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("substituteReqList")
    var substituteReqList: List<SubstituteReqToListData> = arrayListOf()
)
data class SubstituteReqToListData(
    @SerializedName("substituteReqId")
    val substituteReqId: Long,
    @SerializedName("senderId")
    val senderId: Long,
    @SerializedName("senderName")
    val senderName: String,
    @SerializedName("senderImageUrl")
    val senderImageUrl: String,
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("workspaceName")
    val workspaceName: String,
    @SerializedName("startDateTime")
    val startDateTime: String,
    @SerializedName("endDateTime")
    val endDateTime: String,
    @SerializedName("status")
    val status: String
)

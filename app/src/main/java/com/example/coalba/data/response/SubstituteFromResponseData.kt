package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class SubstituteFromResponseData(
    @SerializedName("totalSubstituteReqList")
    var totalSubstituteReqList: List<TotalSubstituteReqListData> = arrayListOf()
)
data class TotalSubstituteReqListData(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("substituteReqList")
    var substituteReqList: List<SubstituteReqListData> = arrayListOf()
)
data class SubstituteReqListData(
    @SerializedName("substituteReqId")
    val substituteReqId: Long,
    @SerializedName("receiverId")
    val receiverId: Long,
    @SerializedName("receiverName")
    val receiverName: String,
    @SerializedName("receiverImageUrl")
    val receiverImageUrl: String,
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

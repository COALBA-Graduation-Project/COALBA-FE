package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class SubstituteDetailResponseData(
    @SerializedName("substituteReqId")
    val substituteReqId: Long,
    @SerializedName("reqMessage")
    val reqMessage: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("senderId")
    val senderId: Long,
    @SerializedName("senderName")
    val senderName: String,
    @SerializedName("senderImageUrl")
    val senderImageUrl: String,
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
    val endDateTime: String
)

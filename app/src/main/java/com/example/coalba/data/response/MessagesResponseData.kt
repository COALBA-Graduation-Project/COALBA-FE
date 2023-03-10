package com.example.coalba.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessagesResponseData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("workspaceName")
    val workspaceName: String,
    @SerializedName("workspaceImageUrl")
    val workspaceImageUrl: String,
    @SerializedName("messageList")
    var messageList: List<MessageListData> = arrayListOf()
): Parcelable
@Parcelize
data class MessageListData(
    @SerializedName("messageId")
    val messageId: Long,
    @SerializedName("sendingOrReceiving")
    val sendingOrReceiving: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createDate")
    val createDate: String
): Parcelable

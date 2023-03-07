package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class MessagesResponseData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("workspaceName")
    val workspaceName: String,
    @SerializedName("workspaceImageUrl")
    val workspaceImageUrl: String,
    @SerializedName("messageList")
    var messageList: List<MessageListData> = arrayListOf()
)
data class MessageListData(
    @SerializedName("messageId")
    val messageId: Long,
    @SerializedName("sendingOrReceiving")
    val sendingOrReceiving: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createDate")
    val createDate: String
)

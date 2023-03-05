package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class WorkspaceLookResponseData(
    @SerializedName("workspaceList")
    var workspaceList: List<WorkspaceListData> = arrayListOf()
)
data class WorkspaceListData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)

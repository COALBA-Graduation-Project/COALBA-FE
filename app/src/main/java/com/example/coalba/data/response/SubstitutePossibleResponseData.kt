package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class SubstitutePossibleResponseData(
    @SerializedName("staffList")
    var staffList: List<StaffListData> = arrayListOf()
)
data class StaffListData(
    @SerializedName("staffId")
    val staffId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)

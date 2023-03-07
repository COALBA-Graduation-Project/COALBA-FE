package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class WorkHistoryDateResponseData(
    @SerializedName("yearList")
    var yearList: List<Int> = arrayListOf()
)

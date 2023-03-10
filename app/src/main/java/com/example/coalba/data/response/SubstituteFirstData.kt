package com.example.coalba.data.response

import com.google.gson.annotations.SerializedName

data class SubstituteFirstData(
    @SerializedName("substituteReqId")
    val substituteReqId : Long,
    @SerializedName("img")
    val img : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("workname")
    val workname : String,
    @SerializedName("starttime")
    val starttime : String,
    @SerializedName("endtime")
    val endtime : String,
    @SerializedName("status")
    val status : String
)

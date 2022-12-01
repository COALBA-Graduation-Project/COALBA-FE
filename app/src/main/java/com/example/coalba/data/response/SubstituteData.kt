package com.example.coalba.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubstituteData (
    @SerializedName("img") val img : Int,
    @SerializedName("name") val name : String,
    @SerializedName("workname") val workname : String,
    @SerializedName("date") val date : String,
    @SerializedName("starttime") val starttime : String,
    @SerializedName("endtime") val endtime : String,
    @SerializedName("state") val state : String
    ) : Parcelable
package com.example.coalba.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreListForMessageData(
    @SerializedName("workspaceId")
    val workspaceId : Long,
    @SerializedName("img")
    val img : String,
    @SerializedName("name")
    val name : String) : Parcelable

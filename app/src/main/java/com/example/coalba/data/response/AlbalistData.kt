package com.example.coalba.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbalistData (
    @SerializedName("name")val name : String) : Parcelable
package com.example.coalba.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScheduleData (
    val scheduleId : Long,
    val name : String,
    val starttime : String,
    val endtime : String,
    val status : String,
    val isMySchedule : Boolean
): Parcelable
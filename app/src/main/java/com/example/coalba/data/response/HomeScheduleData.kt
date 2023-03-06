package com.example.coalba.data.response

data class HomeScheduleData (
    val scheduleId : Long,
    val workname : String,
    val starttime : String,
    val endtime : String,
    val state : String
)
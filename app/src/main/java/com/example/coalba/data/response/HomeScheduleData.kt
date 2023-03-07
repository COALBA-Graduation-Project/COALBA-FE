package com.example.coalba.data.response

data class HomeScheduleData (
    var scheduleId : Long,
    var workname : String,
    var starttime : String,
    var endtime : String,
    var logicalStartTime: String?,
    var logicalEndTime: String?,
    var state : String
)
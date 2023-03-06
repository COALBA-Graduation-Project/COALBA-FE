package com.example.coalba.data.response

data class WeekCalendarData(
    var year: Int,
    var month: Int,
    var day: String,     // 요일
    var date: Int,    // 날짜
    var status: String
)

package com.example.coalba.data.response

data class SubstituteFirstYearmonthData(
    val year : String,
    val month : String,
    val substituteReqList : MutableList<SubstituteFirstData>
)

package com.example.coalba.data.response

data class SubstituteSecondYearmonthData(
    val year : String,
    val month : String,
    val substituteReqList : MutableList<SubstituteSecondData>
)

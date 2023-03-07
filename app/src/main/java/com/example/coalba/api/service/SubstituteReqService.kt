package com.example.coalba.api.service

import com.example.coalba.data.response.SubstitutePossibleResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SubstituteReqService {
    // 대타근무 관련 service
    // 대타근무 요청 가능한 알바 리스트 조회 => 아직
    @GET("staff/substituteReqs/possible/staffs")
    fun substitutePossible(@Query("scheduleId") scheduleId: Long) : Call<SubstitutePossibleResponseData>
}
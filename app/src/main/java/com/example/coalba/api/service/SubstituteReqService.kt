package com.example.coalba.api.service

import com.example.coalba.data.request.SubstituteSendData
import com.example.coalba.data.response.SubstitutePossibleResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SubstituteReqService {
    // 대타근무 관련 service
    // 대타근무 요청 가능한 알바 리스트 조회
    @GET("staff/substituteReqs/possible/staffs")
    fun substitutePossible(@Query("scheduleId") scheduleId: Long) : Call<SubstitutePossibleResponseData>

    // 대타근무 요청 생성 (나의 스케줄인 경우에만)
    @POST("staff/substituteReqs")
    fun substituteSend(@Query("scheduleId") scheduleId: Long, @Query("receiverId") receiverId: Long, @Body data : SubstituteSendData) : Call<Void>
}
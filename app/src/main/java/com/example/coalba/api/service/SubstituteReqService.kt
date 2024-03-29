package com.example.coalba.api.service

import com.example.coalba.data.request.SubstituteSendData
import com.example.coalba.data.response.*
import retrofit2.Call
import retrofit2.http.*

interface SubstituteReqService {
    // 대타근무 관련 service
    // 대타근무 요청 가능한 알바 리스트 조회
    @GET("staff/substituteReqs/possible/staffs")
    fun substitutePossible(@Query("scheduleId") scheduleId: Long) : Call<SubstitutePossibleResponseData>

    // 내가 보낸 대타근무 요청 관리 리스트 조회(from. 나)
    @GET("staff/substituteReqs/from")
    fun substituteFrom() : Call<SubstituteFromResponseData>

    // 내가 받은 대타근무 요청 관리 리스트 조회(to. 나)
    @GET("staff/substituteReqs/to")
    fun substituteTo() : Call<SubstituteToResponseData>

    // 대타근무 요청 관리 상세 조회
    @GET("staff/substituteReqs/{substituteReqId}")
    fun substituteDetail(@Path("substituteReqId") substituteReqId: Long) : Call<SubstituteDetailResponseData>

    // 대타근무 요청 생성 (나의 스케줄인 경우에만)
    @POST("staff/substituteReqs")
    fun substituteSend(@Query("scheduleId") scheduleId: Long, @Query("receiverId") receiverId: Long, @Body data : SubstituteSendData) : Call<Void>

    // 대타근무 요청 취소 (from. 나, 상대가 수락 아직 X)
    @PUT("staff/substituteReqs/{substituteReqId}/cancel")
    fun substituteCancel(@Path("substituteReqId") substituteReqId: Long) : Call<Void>

    // 대타근무 요청 수락
    @PUT("staff/substituteReqs/{substituteReqId}/accept")
    fun substituteAccept(@Path("substituteReqId") substituteReqId: Long) : Call<Void>

    // 대타근무 요청 거절
    @PUT("staff/substituteReqs/{substituteReqId}/reject")
    fun substituteReject(@Path("substituteReqId") substituteReqId: Long) : Call<Void>
}
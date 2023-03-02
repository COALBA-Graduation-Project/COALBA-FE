package com.example.coalba.api.service

import com.example.coalba.data.request.NotificationRequestData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {
    // 알림 관련 service
    // 알림 토큰 전달
    @POST("notification")
    fun notification(@Body data : NotificationRequestData) : Call<Void>
}
package com.example.coalba.api.service

import com.example.coalba.data.request.AuthRequestData
import com.example.coalba.data.response.AuthResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    // 서버에 로그인 요청을 위한 service
    @POST("auth/login")
    fun login(@Query("provider") provider: String, @Query("role") role: String, @Body request: AuthRequestData) : Call<AuthResponseData>
}
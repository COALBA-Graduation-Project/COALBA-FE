package com.example.coalba.api.service

import com.example.coalba.data.request.GoogleLoginRequestData
import com.example.coalba.data.response.GoogleLoginResponseData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleLoginService {
    // google access_token, refresh_token 발급 요청 위한 service
    @POST("oauth2/v4/token")
    fun getToken(@Body request: GoogleLoginRequestData): Call<GoogleLoginResponseData>

    //기존 RetrofitClient 싱글톤이기 때문에 baseUrl 값이 다른 Retrofit 객체 생성 후 반환
    companion object {
        val instance: GoogleLoginService = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleLoginService::class.java)
    }
}
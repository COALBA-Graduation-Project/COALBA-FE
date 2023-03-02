package com.example.coalba.api.interceptor

import com.example.coalba.api.jwt.CoalbaApplication
import okhttp3.Interceptor
import okhttp3.Response

// 서버에 REST API를 요청할 때 interceptor가 요청을 가로채 헤더에 accessToken를 추가해주는 역할
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // chain에 "Authorization"이라는 key와 prefs의 value를 가진 헤더를 붙인 뒤 return
        var request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + CoalbaApplication.prefs.accessToken).build()
        return chain.proceed(request)
    }
}
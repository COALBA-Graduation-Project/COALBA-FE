package com.example.coalba.api.retrofit

import com.example.coalba.api.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 싱글톤 패턴 : 어떤 클래스의 인스턴스는 오직 하나임을 보장, 이 인스턴스는 전역에서 접근할 수 있는 디자인 패턴
// 프로세스가 메모리 상에 올라갈 때 곧바로 생성
object RetrofitClient {
    private val BASE_URL = "http://ec2-3-36-162-247.ap-northeast-2.compute.amazonaws.com:8080/api/"
    private var retrofitClient: Retrofit? = null
    // interceptor 등록
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

    fun getRetrofitClient() : Retrofit? {
        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }
}
package com.example.coalba.api.jwt

import android.content.Context

// Shared Preference : 간단한 설정 값을 앱 내부의 DB에 저장하기 용이한 내부 저정소 (앱 삭제시 데이터도 소거)
class CoalbaSharedPreference (context: Context) {
    // JWT: JSON Web Token의 약자, 안정성 있게 데이터를 교환하기 위해 사용
    // 로그인 시 서버에서 토큰을 발급받고 정보를 주고 받을 때 헤더에 토큰을 사용하여 데이터를 주고 받는다.
    // JWT 사용과정
    // 1. 아이디 비밀번호 등을 통해 사용자가 로그인함 2. 서버에서 토큰(access token)을 발급
    // 3. 토큰을 저장소(preference..)에 저장 4. 서버에 요청할 때 저장소의 토큰을 header에 넣어 요청

    // preference 인스턴스 생성
    private val prefName = "mPref"
    private val prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    // get, set 메서드를 통해 관리
    var accessToken: String?
        get() = prefs.getString("accessToken", null)
        set(value) {
            prefs.edit().putString("accessToken", value).apply()
        }

    var refreshToken: String?
        get() = prefs.getString("refreshToken", null)
        set(value) {
            prefs.edit().putString("refreshToken", value).apply()
        }
}
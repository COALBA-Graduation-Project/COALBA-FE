package com.example.coalba.data.response

data class AuthResponseData(
    val accessToken : String,
    val refreshToken : String,
    val isNewUser : Boolean
)

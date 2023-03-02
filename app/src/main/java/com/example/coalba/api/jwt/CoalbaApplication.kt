package com.example.coalba.api.jwt

import android.app.Application

class CoalbaApplication : Application() {

    companion object {
        lateinit var prefs: CoalbaSharedPreference
    }

    override fun onCreate() {
        prefs = CoalbaSharedPreference(applicationContext)
        super.onCreate()
    }
}
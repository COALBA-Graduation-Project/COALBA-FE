package com.example.coalba.api.retrofit

import com.example.coalba.api.service.AuthService
import com.example.coalba.api.service.NotificationService
import com.example.coalba.api.service.ProfileService
import com.example.coalba.api.service.WorkspaceService

class RetrofitManager {
    companion object {
        val authService = RetrofitClient.getRetrofitClient()?.create(AuthService::class.java)
        val profileService = RetrofitClient.getRetrofitClient()?.create(ProfileService::class.java)
        val workspaceService = RetrofitClient.getRetrofitClient()?.create(WorkspaceService::class.java)
        // val scheduleService = RetrofitClient.getRetrofitClient()?.create(ScheduleService::class.java)
        val notificationService = RetrofitClient.getRetrofitClient()?.create(NotificationService::class.java)
    }
}
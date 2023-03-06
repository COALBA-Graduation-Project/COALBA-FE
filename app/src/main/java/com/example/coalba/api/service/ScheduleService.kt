package com.example.coalba.api.service

import com.example.coalba.data.response.ScheduleCalendarResponseData
import com.example.coalba.data.response.ScheduleDateResponseData
import com.example.coalba.data.response.ScheduleMainResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleService {
    // 스케줄 관련 service
    // 홈 달력 정보 조회 & 오늘 스케줄 조회
    @GET("staff/schedules/home")
    fun scheduleMain() : Call<ScheduleMainResponseData>

    // 홈 해당 날짜 스케줄 조회
    @GET("staff/schedules/home/selected")
    fun scheduleDate(@Query("year") year: Int, @Query("month") month: Int, @Query("day") day: Int) : Call<ScheduleDateResponseData>

    // 해당 워크스페이스 홈 달력 정보 조회 => 아직
    @GET("staff/schedules")
    fun scheduleCalendar(@Query("workspaceId") workspaceId: Long) : Call<ScheduleCalendarResponseData>
}
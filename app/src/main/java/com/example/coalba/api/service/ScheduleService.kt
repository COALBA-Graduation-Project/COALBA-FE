package com.example.coalba.api.service

import com.example.coalba.data.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleService {
    // 스케줄 관련 service
    // 홈 달력 정보 조회 & 오늘 스케줄 조회
    @GET("staff/schedules/home")
    fun scheduleMain() : Call<ScheduleMainResponseData>

    // 홈 해당 날짜 스케줄 조회
    @GET("staff/schedules/home/selected")
    fun scheduleDate(@Query("year") year: Int, @Query("month") month: Int, @Query("day") day: Int) : Call<ScheduleDateResponseData>

    // 해당 워크스페이스 홈 달력 정보 조회
    @GET("staff/schedules")
    fun scheduleCalendar(@Query("workspaceId") workspaceId: Long) : Call<ScheduleCalendarResponseData>

    // 해당 워크스페이스 홈 해당 날짜 스케줄 조회
    @GET("staff/schedules/selected")
    fun scheduleEachWorkspaceSchedule(@Query("workspaceId") workspaceId: Long, @Query("year") year: Int, @Query("month") month: Int, @Query("day") day: Int) : Call<ScheduleEachWorkspaceScheduleResponseData>

    // 해당 스케줄 요약 조회
    @GET("staff/schedules/{scheduleId}")
    fun scheduleSummary(@Path("scheduleId") scheduleId: Long) : Call<ScheduleSummaryResponseData>

    // 해당 스케줄 출근 요청 => 아직
    @PUT("staff/schedules/{scheduleId}/start")
    fun scheduleStart(@Path("scheduleId") scheduleId: Long) : Call<ScheduleStartResponseData>

    // 해당 스케줄 퇴근 요청 => 아직
    @PUT("staff/schedules/{scheduleId}/end")
    fun scheduleEnd(@Path("scheduleId") scheduleId: Long) : Call<ScheduleEndResponseData>
}
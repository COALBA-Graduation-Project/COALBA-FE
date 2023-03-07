package com.example.coalba.api.service

import com.example.coalba.data.request.MessageSendData
import com.example.coalba.data.response.MessagesResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessageService {
    // 메시지 관련 service
    // 해당 워크스페이스 쪽지함 내 메시지 리스트 조회 (최신순)
    @GET("staff/messages")
    fun messages(@Query("workspaceId") workspaceId: Long) : Call<MessagesResponseData>

    // 해당 워크스페이스에 쪽지 보내기
    @POST("staff/messages")
    fun messageSend(@Query("workspaceId") workspaceId: Long,  @Body data : MessageSendData) : Call<Void>
}
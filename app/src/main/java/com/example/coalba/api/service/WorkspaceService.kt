package com.example.coalba.api.service

import com.example.coalba.data.response.WorkspaceLookResponseData
import retrofit2.Call
import retrofit2.http.GET

interface WorkspaceService {
    // 워크스페이스 관련 service
    // 나의 워크스페이스 리스트 조회 => 아직
    @GET("staff/workspaces")
    fun workspaceLook() : Call<WorkspaceLookResponseData>
}
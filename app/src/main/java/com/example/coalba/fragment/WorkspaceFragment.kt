package com.example.coalba.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coalba.R
import com.example.coalba.adapter.AlbaListAdapter
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.AlbalistData
import com.example.coalba.data.response.WorkspaceLookResponseData
import com.example.coalba.databinding.FragmentMessageBinding
import com.example.coalba.databinding.FragmentWorkspaceBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkspaceFragment : Fragment() {

    private lateinit var binding: FragmentWorkspaceBinding
    lateinit var albaListAdapter: AlbaListAdapter
    val datas = mutableListOf<AlbalistData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkspaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 나의 워크스페이스 리스트 조회 서버 연동
        RetrofitManager.workspaceService?.workspaceLook()?.enqueue(object:
            Callback<WorkspaceLookResponseData> {
            override fun onResponse(
                call: Call<WorkspaceLookResponseData>,
                response: Response<WorkspaceLookResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("WorkspaceLook", "success")
                    val data = response.body()
                    val num = data!!.workspaceList.count()
                    albaListAdapter = AlbaListAdapter(requireContext())
                    binding.rvAlbalist.adapter = albaListAdapter

                    if (num == 0){
                        Toast.makeText(requireContext(), "존재하는 워크스페이스가 없습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        for(i in 0..num-1){
                            val itemdata = response.body()?.workspaceList?.get(i)
                            datas.add(AlbalistData(itemdata!!.workspaceId,itemdata!!.imageUrl, itemdata!!.name))
                        }
                        albaListAdapter.datas=datas
                        albaListAdapter.notifyDataSetChanged()
                    }
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("WorkspaceLook", "fail")
                }
            }
            override fun onFailure(call: Call<WorkspaceLookResponseData>, t: Throwable) {
                Log.d("WorkspaceLook", "error")
            }
        })
    }
}
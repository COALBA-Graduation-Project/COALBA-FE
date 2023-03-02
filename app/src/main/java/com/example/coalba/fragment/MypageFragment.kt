package com.example.coalba.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.coalba.ProfileModifyActivity
import com.example.coalba.R
import com.example.coalba.SubstituteActivity
import com.example.coalba.WorkHistoryActivity
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.ProfileLookResponseData
import com.example.coalba.databinding.FragmentMypageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentMypageBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var sendData: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvMypageManage1.setOnClickListener {
            val intent = Intent(requireContext(), WorkHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.tvMypageManage2.setOnClickListener {
            val intent = Intent(requireContext(), SubstituteActivity::class.java)
            startActivity(intent)
        }

        binding.btnMypage.setOnClickListener {
            val intent = Intent(requireContext(), ProfileModifyActivity::class.java)
            intent.putExtra("prevImageUrl", sendData)
            startActivity(intent)
        }

        // 프로필 조회 서버 연동
        RetrofitManager.profileService?.profileLook()?.enqueue(object:
            Callback<ProfileLookResponseData> {
            override fun onResponse(
                call: Call<ProfileLookResponseData>,
                response: Response<ProfileLookResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("ProfileLook", "success")
                    val data = response.body()
                    binding.tvMypageName.text = data!!.realName
                    sendData = data!!.imageUrl
                    Glide.with(this@MypageFragment).load(data!!.imageUrl).into(binding.ivMypageProfile)

                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("ProfileLook", "fail")
                }
            }
            override fun onFailure(call: Call<ProfileLookResponseData>, t: Throwable) {
                Log.d("ProfileLook", "error")
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }
}
package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.SubstituteDetailResponseData
import com.example.coalba.databinding.ActivitySubstituteDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubstituteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubstituteDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubstituteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val subreqId = intent.getLongExtra("substitutereqId", 0)
        val divideVal = intent.getStringExtra("divide")

        if (divideVal == "REFUSAL"){
            binding.tvSubstituteDetailState.text = "거절"
            binding.tvSubstituteDetailState.setTextColor(ContextCompat.getColor(this, R.color.refuse))
        }
        else if (divideVal == "APPROVAL"){
            binding.tvSubstituteDetailState.text = "승인"
        }
        else if (divideVal == "DISAPPROVAL"){
            binding.tvSubstituteDetailState.text = "비승인"
            binding.tvSubstituteDetailState.setTextColor(ContextCompat.getColor(this, R.color.refuse))
        }
        else if (divideVal == "CANCELLATION"){
            binding.tvSubstituteDetailState.text = "취소"
            binding.tvSubstituteDetailState.setTextColor(ContextCompat.getColor(this, R.color.gray))
        }
        else {
            binding.tvSubstituteDetailState.text = "수락"
        }

        // 대타근무 요청 관리 상세 조회
        RetrofitManager.substituteReqService?.substituteDetail(subreqId)?.enqueue(object:
            Callback<SubstituteDetailResponseData> {
            override fun onResponse(
                call: Call<SubstituteDetailResponseData>,
                response: Response<SubstituteDetailResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("SubstituteDetail", "success")
                    val data = response.body()
                    Glide.with(this@SubstituteDetailActivity).load(data!!.senderImageUrl).into(binding.ivSubstituteDetailProfile)
                    binding.tvSubstituteDetailName.text = data.senderName
                    Glide.with(this@SubstituteDetailActivity).load(data.receiverImageUrl).into(binding.ivSubstituteDetailRecvprofile)
                    binding.tvSubstituteDetailRecvname.text = data.receiverName
                    binding.tvSubstituteDetailPlace.text = data.workspaceName
                    binding.tvSubstituteDetailStarttime.text = data.startDateTime
                    binding.tvSubstituteDetailEndtime.text = data.endDateTime
                    binding.etSubstituteDetailMessage.setText(data.reqMessage)
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("SubstituteDetail", "fail")
                }
            }
            override fun onFailure(call: Call<SubstituteDetailResponseData>, t: Throwable) {
                Log.d("SubstituteDetail", "error")
            }
        })

        binding.ivSubstituteDetailBack.setOnClickListener {
            finish()
        }
    }
}
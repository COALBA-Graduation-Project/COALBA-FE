package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.ScheduleSummaryResponseData
import com.example.coalba.data.response.SubstituteDetailResponseData
import com.example.coalba.databinding.ActivitySubstituteDetailBinding
import com.example.coalba.databinding.ActivitySubstituteProcessBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubstituteProcessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubstituteProcessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubstituteProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val subreqId = intent.getLongExtra("substitutereqId", 0)
        val divideNum = intent.getIntExtra("divide", 0)
        if (divideNum == 1) binding.btnSubstituteProcessCancel.isVisible = true
        else if (divideNum == 2) binding.btnSubstituteProcessCancel.isVisible = false

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
                    Glide.with(this@SubstituteProcessActivity).load(data!!.senderImageUrl).into(binding.ivSubstituteProcessProfile)
                    binding.tvSubstituteProcessName.text = data.senderName
                    Glide.with(this@SubstituteProcessActivity).load(data.receiverImageUrl).into(binding.ivSubstituteProcessRecvprofile)
                    binding.tvSubstituteProcessRecvname.text = data.receiverName
                    binding.tvSubstituteProcessPlace.text = data.workspaceName
                    binding.tvSubstituteProcessStarttime.text = data.startDateTime
                    binding.tvSubstituteProcessEndtime.text = data.endDateTime
                    binding.etSubstituteProcessMessage.setText(data.reqMessage)
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("SubstituteDetail", "fail")
                }
            }
            override fun onFailure(call: Call<SubstituteDetailResponseData>, t: Throwable) {
                Log.d("SubstituteDetail", "error")
            }
        })

        binding.ivSubstituteProcessBack.setOnClickListener {
            finish()
        }
        /*
        binding.btnSubstituteProcessCancel.setOnClickListener {

        }*/
    }
}
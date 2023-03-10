package com.example.coalba

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.SubstituteDetailResponseData
import com.example.coalba.databinding.ActivitySubstituteProcessBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubstituteProcessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubstituteProcessBinding
    var subreqId : Long = 0

    // 취소 다이얼로그
    val positiveCancelBtnClick = { dialogInterface: DialogInterface, i: Int ->
        // 대타근무 요청 취소 서버 연동
        RetrofitManager.substituteReqService?.substituteCancel(subreqId)?.enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("ScheduleCancel", "success")
                    finish()
                } else { // 이곳은 에러 발생할 경우 실행됨
                    Log.d("ScheduleCancel", "fail")
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
            }
        })
        Toast.makeText(this, "취소되었습니다", Toast.LENGTH_SHORT).show()
    }
    val negativeCancelBtnClick = { dialogInterface: DialogInterface, i: Int ->
        // Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
    }

    // 거절 다이얼로그
    val positiveRejectBtnClick = { dialogInterface: DialogInterface, i: Int ->
        // 대타근무 요청 거절 서버 연동
        RetrofitManager.substituteReqService?.substituteReject(subreqId)?.enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("ScheduleReject", "success")
                    finish()
                } else { // 이곳은 에러 발생할 경우 실행됨
                    Log.d("ScheduleReject", "fail")
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("ScheduleReject", "error")
            }
        })
        Toast.makeText(this, "거절되었습니다", Toast.LENGTH_SHORT).show()
    }
    val negativeRejectBtnClick = { dialogInterface: DialogInterface, i: Int ->
        Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
    }

    // 수락 다이얼로그
    val positiveAllowBtnClick = { dialogInterface: DialogInterface, i: Int ->
        // 대타근무 요청 수락 서버 연동
        RetrofitManager.substituteReqService?.substituteAccept(subreqId)?.enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("ScheduleAccept", "success")
                    finish()
                } else { // 이곳은 에러 발생할 경우 실행됨
                    val data1 = response.code()
                    Log.d("status code", data1.toString())
                    val data2 = response.headers()
                    Log.d("header", data2.toString())
                    Log.d("server err", response.errorBody()?.string().toString())
                    Log.d("ScheduleAccept", "fail")
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("ScheduleAccept", "error")
            }
        })
        Toast.makeText(this, "수락되었습니다", Toast.LENGTH_SHORT).show()
    }
    val negativeAllowBtnClick = { dialogInterface: DialogInterface, i: Int ->
        Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubstituteProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subreqId = intent.getLongExtra("substitutereqId", 0)
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

        binding.btnSubstituteProcessCancel.setOnClickListener {
            // 취소 확인 다이얼로그 띄우기
            val builder = AlertDialog.Builder(this)
            builder.setTitle("정말 취소하시겠습니까?")
                .setMessage("해당 대타근무 요청을 취소합니다.")
                .setPositiveButton("확인", positiveCancelBtnClick)
                .setNegativeButton("취소", negativeCancelBtnClick)
            val alertDialog = builder.create()
            alertDialog.show()
            val button1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            with(button1){
                setTextColor(Color.RED)
            }
            val button2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            with(button2){
                setTextColor(Color.BLUE)
            }
        }
        binding.btnSubstituteProcessReject.setOnClickListener {
            // 거절 확인 다이얼로그 띄우기
            val builder = AlertDialog.Builder(this)
            builder.setTitle("정말 거절하시겠습니까?")
                .setMessage("해당 대타근무 요청을 거절합니다.")
                .setPositiveButton("확인", positiveRejectBtnClick)
                .setNegativeButton("취소", negativeRejectBtnClick)
            val alertDialog = builder.create()
            alertDialog.show()
            val button1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            with(button1){
                setTextColor(Color.RED)
            }
            val button2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            with(button2){
                setTextColor(Color.BLUE)
            }
        }
        binding.btnSubstituteProcessAllow.setOnClickListener {
            // 수락 확인 다이얼로그 띄우기
            val builder = AlertDialog.Builder(this)
            builder.setTitle("정말 수락하시겠습니까?")
                .setMessage("해당 대타근무 요청을 수락합니다.")
                .setPositiveButton("확인", positiveAllowBtnClick)
                .setNegativeButton("취소", negativeAllowBtnClick)
            val alertDialog = builder.create()
            alertDialog.show()
            val button1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            with(button1){
                setTextColor(Color.RED)
            }
            val button2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            with(button2){
                setTextColor(Color.BLUE)
            }
        }
    }
}
package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coalba.adapter.SubstituteAddAdapter
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.request.SubstituteSendData
import com.example.coalba.data.response.*
import com.example.coalba.databinding.ActivitySubstituteRequestBinding
import com.example.coalba.databinding.ActivityWorkspaceHomeBinding
import com.example.coalba.databinding.DialogSubstitutePersonBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubstituteRequestActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivitySubstituteRequestBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var sId : Long = 0
    var albaId : Long = 0
    lateinit var substituteAdapter: SubstituteAddAdapter
    val datas = mutableListOf<SubstituteAddPersonData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivitySubstituteRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ScheduleData>("ForSubstitute")
        sId = data!!.scheduleId

        // 해당 스케줄 요약 조회 서버 연동
        RetrofitManager.scheduleService?.scheduleSummary(sId)?.enqueue(object:
            Callback<ScheduleSummaryResponseData> {
            override fun onResponse(
                call: Call<ScheduleSummaryResponseData>,
                response: Response<ScheduleSummaryResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("ScheduleSummary", "success")
                    val data2 = response.body()
                    binding.tvSubstituteRequestPlace.text = data2!!.workspaceName
                    binding.tvSubstituteRequestDate.text = data2.scheduleDate
                    binding.tvSubstituteRequestStarttime.text = data2.scheduleStartTime
                    binding.tvSubstituteRequestEndtime.text = data2.scheduleEndTime
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("ScheduleSummary", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleSummaryResponseData>, t: Throwable) {
                Log.d("ScheduleSummary", "error")
            }
        })

        binding.ivSubstituteRequestBack.setOnClickListener {
            finish()
        }
        // 대타근무 요청 parttime person pick
        val putPersonDialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
        substituteAdapter = SubstituteAddAdapter(this, object: SubstituteAddAdapter.PossiblePersonListener{
            override fun click(staffId: Long, name: String) {
                binding.tvSubstitutePutperson.apply {
                    text = name
                    setTextColor(getColor(R.color.black))
                }
                binding.tvSubstituteRequestPerson.apply{
                    text = name
                    setTextColor(getColor(R.color.black))
                }
                albaId = staffId
                putPersonDialog.dismiss()
            }
        })
        binding.clPerson.setOnClickListener {
            val sheetView = DialogSubstitutePersonBinding.inflate(layoutInflater)

            // 대타근무 요청 가능한 알바 리스트 조회 서버 연동
            RetrofitManager.substituteReqService?.substitutePossible(sId)?.enqueue(object :
                Callback<SubstitutePossibleResponseData> {
                override fun onResponse(
                    call: Call<SubstitutePossibleResponseData>,
                    response: Response<SubstitutePossibleResponseData>
                ) {
                    if (response.isSuccessful) {
                        Log.d("SubstitutePossible", "success")
                        val data = response.body()
                        val num = data!!.staffList.count()
                        if (num == 0) {
                            Toast.makeText(this@SubstituteRequestActivity, "가능한 알바생이 없습니다!", Toast.LENGTH_SHORT).show()
                        } else {
                            for (i in 0..num - 1) {
                                val itemdata = response.body()?.staffList?.get(i)
                                datas.add(SubstituteAddPersonData(itemdata!!.staffId, itemdata.imageUrl, itemdata.name))
                            }
                            substituteAdapter.datas = datas
                            substituteAdapter.notifyDataSetChanged()
                        }
                    } else { // 이곳은 에러 발생할 경우 실행됨
                        Log.d("SubstitutePossible", "fail")
                    }
                }

                override fun onFailure(call: Call<SubstitutePossibleResponseData>, t: Throwable) {
                    Log.d("SubstitutePossible", "error")
                }
            })
            sheetView.rvSubstitutePerson.adapter = substituteAdapter

            putPersonDialog.setContentView(sheetView.root)
            putPersonDialog.show()
        }
        // todo: 모든 값이 입력되었을때 버튼 색 변경 및 활성화

        // 대타근무 요청 생성 (나의 스케줄인 경우에만)
        binding.btnSubstituteRequest.setOnClickListener {
            RetrofitManager.substituteReqService?.substituteSend(sId, albaId, SubstituteSendData(binding.etSubstituteRequestMessage.text.toString()))?.enqueue(object :
                Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("SubstituteSend", "success")
                        finish()
                    } else { // 이곳은 에러 발생할 경우 실행됨
                        Log.d("SubstituteSend", "fail")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("SubstituteSend", "error")
                }
            })
        }
    }
}
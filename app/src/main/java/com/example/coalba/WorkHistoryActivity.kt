package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.coalba.adapter.WorkHistoryAdapter
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.WorkHistoryData
import com.example.coalba.data.response.WorkHistoryDateResponseData
import com.example.coalba.data.response.WorkHistoryListResponseData
import com.example.coalba.databinding.ActivityMainBinding
import com.example.coalba.databinding.ActivityWorkHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkHistoryActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityWorkHistoryBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var workhistoryAdapter: WorkHistoryAdapter
    val datas = mutableListOf<WorkHistoryData>()
    var yearArray: Array<String> = emptyArray()
    var yearVar: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityWorkHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        workhistoryAdapter = WorkHistoryAdapter(this)

        binding.ivWorkhistoryBack.setOnClickListener {
            finish()
        }
        // 근무내역 및 알바비 관리 년도 리스트 조회 서버 연동
        RetrofitManager.scheduleService?.workhistoryDate()?.enqueue(object:
            Callback<WorkHistoryDateResponseData> {
            override fun onResponse(
                call: Call<WorkHistoryDateResponseData>,
                response: Response<WorkHistoryDateResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("WorkhistoryDate", "success")
                    val data2 = response.body()
                    val num2 = data2!!.yearList.count()

                    for(i in 0..num2-1){
                        val itemdata = response.body()?.yearList?.get(i)
                        Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                        yearArray = yearArray.plus(itemdata!!.toString() + "년")
                    }
                    // year spinner
                    val yearAdapter = ArrayAdapter(this@WorkHistoryActivity, android.R.layout.simple_spinner_item, yearArray)
                    yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinWorkhistory.adapter = yearAdapter

                    binding.spinWorkhistory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            yearVar = data2.yearList.get(p2)

                            // 해당 년도 근무내역 및 알바비 관리 리스트 조회 서버 연동
                            RetrofitManager.scheduleService.workhistoryList(yearVar).enqueue(object:
                                Callback<WorkHistoryListResponseData> {
                                override fun onResponse(
                                    call: Call<WorkHistoryListResponseData>,
                                    response: Response<WorkHistoryListResponseData>
                                ) {
                                    if(response.isSuccessful){
                                        Log.d("WorkhistoryList", "success")
                                        val data3 = response.body()

                                        datas.clear()
                                        val num3 = data3!!.workReportList.count()
                                        if (num3 == 0){
                                            Toast.makeText(this@WorkHistoryActivity, "근무내역이 없습니다", Toast.LENGTH_SHORT).show()
                                        }
                                        else{
                                            binding.rvWorkhistory.adapter = workhistoryAdapter
                                            for(i in 0..num3-1){
                                                val itemdata = response.body()?.workReportList?.get(i)
                                                Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)

                                                datas.add(WorkHistoryData(month = itemdata!!.month.toString(), hour=itemdata!!.totalWorkTimeHour.toString(), minute = itemdata!!.totalWorkTimeMin.toString(), pay = itemdata!!.totalWorkPay))
                                            }
                                            workhistoryAdapter.datas = datas
                                            workhistoryAdapter.notifyDataSetChanged()
                                        }
                                    }else{ // 이곳은 에러 발생할 경우 실행됨
                                        Log.d("WorkhistoryList", "fail")
                                    }
                                }
                                override fun onFailure(call: Call<WorkHistoryListResponseData>, t: Throwable) {
                                    Log.d("WorkhistoryList", "error")
                                }
                            })
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            Toast.makeText(this@WorkHistoryActivity,"년도를 선택해주세요!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("WorkhistoryDate", "fail")
                }
            }
            override fun onFailure(call: Call<WorkHistoryDateResponseData>, t: Throwable) {
                Log.d("WorkhistoryDate", "error")
            }
        })
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}
package com.example.coalba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.coalba.adapter.MonthAdapter
import com.example.coalba.adapter.ScheduleAdapter
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.AlbalistData
import com.example.coalba.data.response.ScheduleCalendarResponseData
import com.example.coalba.data.response.ScheduleData
import com.example.coalba.data.response.ScheduleEachWorkspaceScheduleResponseData
import com.example.coalba.databinding.ActivityWorkspaceHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class WorkspaceHomeActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityWorkspaceHomeBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    lateinit var scheduleAdapter: ScheduleAdapter
    val datas = mutableListOf<ScheduleData>()
    var storeId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityWorkspaceHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scheduleAdapter = ScheduleAdapter(this)

        // rv_workspacehome 리사이클러뷰는 각 월을 나타낼 리스트로서 가로로 전환하기 위하여 LinearLayoutManager의 Horizontal 속성을 준다
        val monthListManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val monthListAdapter = MonthAdapter()

        binding.rvWorkspacehome.apply {
            layoutManager = monthListManager
            adapter = monthListAdapter
            // scrollToPosition은 리스트를 item의 위치를 지정한 곳에서 시작. 해당 위치에서 리스트를 시작하는 이유는 Adapter부분에서 설명
            scrollToPosition(Int.MAX_VALUE/2)
        }
        // PagerSnapHelper()를 설정함으로써 한 항목씩 스크롤이 되도록 만들 수 있다.
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.rvWorkspacehome)

        val data = intent.getParcelableExtra<AlbalistData>("data")
        binding.tvWorkspacehome.text = data!!.name
        storeId = data.workspaceId

        // 해당 워크스페이스 홈 달력 정보 조회 서버 연동
        RetrofitManager.scheduleService?.scheduleCalendar(storeId)?.enqueue(object:
            Callback<ScheduleCalendarResponseData> {
            override fun onResponse(
                call: Call<ScheduleCalendarResponseData>,
                response: Response<ScheduleCalendarResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("ScheduleCalendar", "success")
                    val data2 = response.body()
                    val num = data2!!.selectedSubPage!!.selectedScheduleList.count()
                    if(num == 0){
                        Toast.makeText(this@WorkspaceHomeActivity, "오늘은 스케줄이 없습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.rvSchedule.adapter = scheduleAdapter

                        for(i in 0..num-1){
                            val itemdata = response.body()?.selectedSubPage?.selectedScheduleList?.get(i)
                            datas.add(ScheduleData(itemdata!!.scheduleId, itemdata.worker!!.name, itemdata.scheduleStartTime, itemdata.scheduleEndTime, itemdata.status, itemdata.isMySchedule))
                        }
                        scheduleAdapter.datas = datas
                        scheduleAdapter.notifyDataSetChanged()
                    }
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("ScheduleCalendar", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleCalendarResponseData>, t: Throwable) {
                Log.d("ScheduleCalendar", "error")
            }
        })
        binding.ivWorkspacehomeBack.setOnClickListener {
            finish()
        }
    }

    fun dayClick(day: String){
        // 해당 워크스페이스 홈 해당 날짜 스케줄 조회 서버 연동
        RetrofitManager.scheduleService?.scheduleEachWorkspaceSchedule(storeId, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)+1, day.toInt())?.enqueue(object:
            Callback<ScheduleEachWorkspaceScheduleResponseData> {
            override fun onResponse(
                call: Call<ScheduleEachWorkspaceScheduleResponseData>,
                response: Response<ScheduleEachWorkspaceScheduleResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("ScheduleCalendarClick", "success")
                    val data = response.body()

                    datas.clear()
                    scheduleAdapter.notifyDataSetChanged()

                    val num = data!!.selectedScheduleList.count()
                    if(num == 0){
                        Toast.makeText(this@WorkspaceHomeActivity, "오늘은 스케줄이 없습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.rvSchedule.adapter = scheduleAdapter

                        for(i in 0..num-1){
                            val itemdata = response.body()?.selectedScheduleList?.get(i)
                            datas.add(ScheduleData(itemdata!!.scheduleId, itemdata!!.worker!!.name, itemdata.scheduleStartTime, itemdata.scheduleEndTime, itemdata.status,itemdata.isMySchedule))
                        }
                        scheduleAdapter.datas = datas
                        scheduleAdapter.notifyDataSetChanged()
                    }
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("ScheduleCalendarClick", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleEachWorkspaceScheduleResponseData>, t: Throwable) {
                Log.d("ScheduleCalendarClick", "error")
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
package com.example.coalba.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*;
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import com.example.coalba.adapter.*
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.*
import com.example.coalba.databinding.FragmentHomeBinding
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.TemporalAdjusters.lastDayOfMonth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentHomeBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    // 주간 캘린더
    lateinit var calendarAdapter: WeekCalendarAdapter
    private var calendarList = ArrayList<WeekCalendarData>()
    // 스케줄
    lateinit var homescheduleAdapter: HomeSchduleAdapter
    val datas = mutableListOf<HomeScheduleData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩
        mBinding = FragmentHomeBinding.inflate(inflater,container,false)
        val root: View = binding.root
        AndroidThreeTen.init(context)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homescheduleAdapter = HomeSchduleAdapter(requireContext(), object: HomeSchduleAdapter.StartClickListener{
            override fun click1(scheduleId: Long, position: Int) {
                // 해당 스케줄 출근 요청
                RetrofitManager.scheduleService?.scheduleStart(scheduleId)?.enqueue(object:
                    Callback<ScheduleStartResponseData> {
                    override fun onResponse(
                        call: Call<ScheduleStartResponseData>,
                        response: Response<ScheduleStartResponseData>
                    ) {
                        if(response.isSuccessful){
                            Log.d("ScheduleStart", "success")
                            val data = response.body()
                            datas[position].logicalStartTime = data!!.logicalStartTime
                            datas[position].state = data.status
                            homescheduleAdapter.notifyDataSetChanged()
                        }else{ // 이곳은 에러 발생할 경우 실행됨
                            Log.d("ScheduleStart", "fail")
                        }
                    }
                    override fun onFailure(call: Call<ScheduleStartResponseData>, t: Throwable) {
                        Log.d("ScheduleStart", "error")
                    }
                })
            }
        }, object: HomeSchduleAdapter.EndClickListener{
            override fun click2(scheduleId: Long, position: Int) {
                // 해당 스케줄 퇴근 요청
                RetrofitManager.scheduleService?.scheduleEnd(scheduleId)?.enqueue(object:
                    Callback<ScheduleEndResponseData> {
                    override fun onResponse(
                        call: Call<ScheduleEndResponseData>,
                        response: Response<ScheduleEndResponseData>
                    ) {
                        if(response.isSuccessful){
                            Log.d("ScheduleEnd", "success")
                            val data = response.body()
                            datas[position].logicalEndTime = data!!.logicalEndTime
                            datas[position].state = data.status
                            homescheduleAdapter.notifyDataSetChanged()
                        }else{ // 이곳은 에러 발생할 경우 실행됨
                            Log.d("ScheduleEnd", "fail")
                        }
                    }
                    override fun onFailure(call: Call<ScheduleEndResponseData>, t: Throwable) {
                        Log.d("ScheduleEnd", "error")
                    }
                })
            }
        })

        calendarAdapter = WeekCalendarAdapter(calendarList, object:WeekCalendarAdapter.HomeDayClickListener {
            override fun click(year: Int, month: Int, day: Int) {
                // 홈 해당 날짜 스케줄 조회 서버 연동
                RetrofitManager.scheduleService?.scheduleDate(year,month,day)?.enqueue(object:
                    Callback<ScheduleDateResponseData> {
                    override fun onResponse(
                        call: Call<ScheduleDateResponseData>,
                        response: Response<ScheduleDateResponseData>
                    ) {
                        if(response.isSuccessful){
                            Log.d("ScheduleDate", "success")
                            val data = response.body()

                            datas.clear() // 기존 데이터 clear
                            val num2 = data!!.selectedScheduleList.count()
                            if (num2 == 0){
                                binding.tvHomeNoschedule.isVisible = true
                                homescheduleAdapter.notifyDataSetChanged()
                            }
                            else{
                                binding.tvHomeNoschedule.isVisible = false
                                datas.addAll(data!!.selectedScheduleList.map {schedule ->
                                    HomeScheduleData(schedule.scheduleId, schedule.workspace!!.name, schedule.scheduleStartTime, schedule.scheduleEndTime, schedule.logicalStartTime, schedule.logicalEndTime, schedule.status)
                                }.toMutableList())
                                // 어댑터에 변경된 데이터 적용
                                binding.rvHomeSchedule.adapter!!.notifyItemRangeChanged(0, datas.size)
                            }
                        }else{ // 이곳은 에러 발생할 경우 실행됨
                            Log.d("ScheduleDate", "fail")
                        }
                    }
                    override fun onFailure(call: Call<ScheduleDateResponseData>, t: Throwable) {
                        Log.d("ScheduleDate", "error")
                    }
                })
            }
        })

        // 홈 달력 정보 조회 서버 연동
        RetrofitManager.scheduleService?.scheduleMain()?.enqueue(object:
            Callback<ScheduleMainResponseData> {
            override fun onResponse(
                call: Call<ScheduleMainResponseData>,
                response: Response<ScheduleMainResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("ScheduleMain", "success")
                    val data = response.body()
                    Log.d("data값", data.toString())

                    calendarList.clear()
                    calendarList.addAll(data!!.dateList.map {date ->
                        WeekCalendarData(date.date!!.year, date.date.month, date.date.dayOfWeek, date.date.day, date.totalScheduleStatus)
                    })
                    val selectedDate = data.selectedSubPage!!.selectedDate!! //선택된 날짜 (오늘 날짜)
                    binding.tvHomeDate.text = "${selectedDate.year}년 ${selectedDate.month}월"
                    binding.rvHomeWeek.adapter = calendarAdapter
                    binding.rvHomeWeek.layoutManager = GridLayoutManager(context, 7)
                    binding.rvHomeWeek.adapter!!.notifyItemRangeChanged(0, calendarList.size)

                    // 스케줄 recyclerview
                    datas.clear()
                    val num3 = data!!.selectedSubPage!!.selectedScheduleList.count()
                    if (num3 == 0){
                        binding.tvHomeNoschedule.isVisible = true
                    }
                    binding.rvHomeSchedule.adapter = homescheduleAdapter
                    datas.addAll(data!!.selectedSubPage!!.selectedScheduleList.map {schedule ->
                        HomeScheduleData(schedule.scheduleId, schedule.workspace!!.name, schedule.scheduleStartTime, schedule.scheduleEndTime, schedule.logicalStartTime, schedule.logicalEndTime, schedule.status)
                    }.toMutableList())
                    homescheduleAdapter.datas=datas
                    homescheduleAdapter.notifyDataSetChanged()

                }else{ // 이곳은 에러 발생할 경우 실행됨 => 401일 경우 token 만료된 것!
                    Log.d("ScheduleMain", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleMainResponseData>, t: Throwable) {
                Log.d("ScheduleMain", "error")
            }
        })
    }
}
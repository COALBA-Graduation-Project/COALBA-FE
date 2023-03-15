package com.example.coalba.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*;
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import com.example.coalba.MainActivity
import com.example.coalba.adapter.*
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.*
import com.example.coalba.databinding.FragmentHomeBinding
import com.example.coalba.databinding.ItemHomeScheduleBinding
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
    var scheduleID1 : Long = 0
    var pos1 : Int = 0
    var scheduleID2 : Long = 0
    var pos2 : Int = 0

    // 출근 다이얼로그
    val positiveComeBtnClick = { dialogInterface: DialogInterface, i: Int ->
        // 해당 스케줄 출근 요청
        RetrofitManager.scheduleService?.scheduleStart(scheduleID1)?.enqueue(object:
            Callback<ScheduleStartResponseData> {
            override fun onResponse(call: Call<ScheduleStartResponseData>, response: Response<ScheduleStartResponseData>) {
                if(response.isSuccessful){
                    Log.d("ScheduleStart", "success")
                    (requireContext() as MainActivity).detectBeacon()

                    val data = response.body()
                    datas[pos1].logicalStartTime = data!!.logicalStartTime
                    datas[pos1].state = data.status
                    homescheduleAdapter.notifyDataSetChanged()
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    val data1 = response.code()
                    Log.d("status code", data1.toString())
                    val data2 = response.headers()
                    Log.d("header", data2.toString())
                    Log.d("server err", response.errorBody()?.string().toString())
                    Log.d("ScheduleStart", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleStartResponseData>, t: Throwable) {
                Log.d("ScheduleStart", "error")
            }
        })
        Toast.makeText(requireContext(), "출근되었습니다", Toast.LENGTH_SHORT).show()
    }
    val negativeComeBtnClick = { dialogInterface: DialogInterface, i: Int ->
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

    // 퇴근 다이얼로그
    val positiveLeaveBtnClick = { dialogInterface: DialogInterface, i: Int ->
        // 해당 스케줄 퇴근 요청
        RetrofitManager.scheduleService?.scheduleEnd(scheduleID2)?.enqueue(object:
            Callback<ScheduleEndResponseData> {
            override fun onResponse(call: Call<ScheduleEndResponseData>, response: Response<ScheduleEndResponseData>) {
                if(response.isSuccessful){
                    Log.d("ScheduleEnd", "success")
                    val data = response.body()
                    datas[pos2].logicalEndTime = data!!.logicalEndTime
                    datas[pos2].state = data.status
                    homescheduleAdapter.notifyDataSetChanged()
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    val data1 = response.code()
                    Log.d("status code", data1.toString())
                    val data2 = response.headers()
                    Log.d("header", data2.toString())
                    Log.d("server err", response.errorBody()?.string().toString())
                    Log.d("ScheduleEnd", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleEndResponseData>, t: Throwable) {
                Log.d("ScheduleEnd", "error")
            }
        })
        Toast.makeText(requireContext(), "퇴근되었습니다", Toast.LENGTH_SHORT).show()
    }
    val negativeLeaveBtnClick = { dialogInterface: DialogInterface, i: Int ->
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

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
                scheduleID1 = scheduleId
                pos1 = position
                // 출근 확인 다이얼로그 띄우기
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("출근하시겠습니까?")
                    .setMessage("해당 스케줄에 출근합니다")
                    .setPositiveButton("확인", positiveComeBtnClick)
                    .setNegativeButton("취소", negativeComeBtnClick)
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
        }, object: HomeSchduleAdapter.EndClickListener{
            override fun click2(scheduleId: Long, position: Int) {
                scheduleID2 = scheduleId
                pos2 = position
                // 퇴근 확인 다이얼로그 띄우기
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("퇴근하시겠습니까?")
                    .setMessage("해당 스케줄에 퇴근합니다")
                    .setPositiveButton("확인", positiveLeaveBtnClick)
                    .setNegativeButton("취소", negativeLeaveBtnClick)
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
        })

        calendarAdapter = WeekCalendarAdapter(calendarList, object:WeekCalendarAdapter.HomeDayClickListener {
            override fun click(year: Int, month: Int, day: Int) {
                binding.tvHomeDate.text = "${year}년 ${month}월" //날짜 클릭 시 년월 텍스트 변경

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
                            homescheduleAdapter.notifyDataSetChanged()
                            val num2 = data!!.selectedScheduleList.count()
                            if (num2 == 0){
                                binding.tvHomeNoschedule.isVisible = true
                            }
                            else{
                                binding.tvHomeNoschedule.isVisible = false
                                datas.addAll(data.selectedScheduleList.map {schedule ->
                                    HomeScheduleData(schedule.scheduleId, schedule.workspace!!.name, schedule.scheduleStartTime, schedule.scheduleEndTime, schedule.logicalStartTime, schedule.logicalEndTime, schedule.status)
                                }.toMutableList())
                            }
                            // 어댑터에 변경된 데이터 적용
                            binding.rvHomeSchedule.adapter!!.notifyItemRangeChanged(0, datas.size)
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
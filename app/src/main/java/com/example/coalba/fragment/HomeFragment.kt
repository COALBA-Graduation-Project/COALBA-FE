package com.example.coalba.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*;
import android.widget.Toast
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
        homescheduleAdapter = HomeSchduleAdapter(requireContext())

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
                            // 이전의 recyclerview 값 전체 지우기
                            datas.removeAll(datas)
                            homescheduleAdapter.notifyDataSetChanged()

                            val num2 = data!!.selectedScheduleList.count()
                            Log.d("num 값", "num 값 " + num2)

                            if (num2 == 0){
                                Toast.makeText(requireContext(), "해당 날짜 스케줄이 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                binding.rvHomeSchedule.adapter = homescheduleAdapter

                                for(i in 0..num2-1){
                                    val itemdata2 = response.body()?.selectedScheduleList?.get(i)
                                    Log.d("responsevalue", "workspace response 값 => "+ itemdata2)

                                    datas.add(HomeScheduleData(itemdata2!!.scheduleId, itemdata2.workspace!!.name, itemdata2.scheduleStartTime, itemdata2.scheduleEndTime, itemdata2.status))
                                }
                                homescheduleAdapter.datas=datas
                                homescheduleAdapter.notifyDataSetChanged()
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
                    // 이전의 recyclerview 값 전체 지우기
                    calendarList.removeAll(calendarList)
                    calendarAdapter.notifyDataSetChanged()

                    val num = data!!.dateList.count()
                    Log.d("num 값", "num 값 " + num)
                    for(i in 0..num-1){
                        val itemdata = response.body()?.dateList?.get(i)
                        Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                        if (i == 3){
                            binding.tvHomeDate.text = itemdata!!.date!!.year.toString() + "년" + itemdata!!.date!!.month.toString() + "월"
                        }
                        calendarList.add(WeekCalendarData(itemdata!!.date!!.year, itemdata!!.date!!.month, itemdata!!.date!!.dayOfWeek, itemdata!!.date!!.day, itemdata!!.totalScheduleStatus))
                    }
                    binding.rvHomeWeek.adapter = calendarAdapter
                    binding.rvHomeWeek.layoutManager = GridLayoutManager(context, 7)

                    val num3 = data.selectedSubPage!!.selectedScheduleList.count()
                    Log.d("num 값", "num 값 " + num3)
                    if (num3 == 0){
                        Toast.makeText(requireContext(), "해당 날짜 스케줄이 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.rvHomeSchedule.adapter = homescheduleAdapter
                        for(i in 0..num3-1){
                            val itemdata3 = response.body()?.selectedSubPage?.selectedScheduleList?.get(i)
                            Log.d("responsevalue", "response 값 => "+ itemdata3)

                            datas.add(HomeScheduleData(itemdata3!!.scheduleId, itemdata3.workspace!!.name, itemdata3.scheduleStartTime, itemdata3.scheduleEndTime, itemdata3.status))
                        }
                        homescheduleAdapter.datas=datas
                        homescheduleAdapter.notifyDataSetChanged()
                    }
                }else{ // 이곳은 에러 발생할 경우 실행됨 => 401일 경우 token 만료된 것!
                    val data1 = response.code()
                    Log.d("status code", data1.toString())
                    val data2 = response.headers()
                    Log.d("header", data2.toString())
                    Log.d("server err", response.errorBody()?.string().toString())
                    Log.d("ScheduleMain", "fail")
                }
            }
            override fun onFailure(call: Call<ScheduleMainResponseData>, t: Throwable) {
                Log.d("ScheduleMain", "error")
            }
        })
    }
}
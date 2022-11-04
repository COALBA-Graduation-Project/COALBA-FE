package com.example.coalba.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.adapter.WeekCalendarAdapter
import com.example.coalba.data.response.ResponseWeekCalendarData
import com.example.coalba.databinding.FragmentHomeBinding
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.TemporalAdjusters.lastDayOfMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*

class HomeFragment : Fragment() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentHomeBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    val itemList = arrayListOf<ResponseWeekCalendarData>()
    val listAdapter = WeekCalendarAdapter(itemList)
    lateinit var calendarList: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager
    var monthNum: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩
        mBinding = FragmentHomeBinding.inflate(inflater,container,false)
        val root: View = binding.root
        calendarList = binding.rvHomeWeek
        mLayoutManager = LinearLayoutManager(root.context)

        // recyclerview orientation (가로 방향 스크롤 설정)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        calendarList.layoutManager = mLayoutManager

        setListView()
        AndroidThreeTen.init(context)
        return root
    }
    // list(날짜, 요일)를 만들고, adapter를 등록하는 메소드
    private fun setListView(){
        // 현재 달의 마지막 날짜
        val lastDayOfMonth = LocalDate.now().with(lastDayOfMonth())
        lastDayOfMonth.format(ofPattern("dd"))

        for(i: Int in 1..lastDayOfMonth.dayOfMonth) {
            val date = LocalDate.of(LocalDate.now().year, LocalDate.now().month, i)
            val dayOfWeek: DayOfWeek = date.dayOfWeek
            dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)
            if(LocalDate.now().month.toString() == "JANUARY"){
                monthNum = "1"
            }
            else if(LocalDate.now().month.toString() == "FEBRUARY"){
                monthNum = "2"
            }
            else if(LocalDate.now().month.toString() == "MARCH"){
                monthNum = "3"
            }
            else if(LocalDate.now().month.toString() == "APRIL"){
                monthNum = "4"
            }
            else if(LocalDate.now().month.toString() == "MAY"){
                monthNum = "5"
            }
            else if(LocalDate.now().month.toString() == "JUNE"){
                monthNum = "6"
            }
            else if(LocalDate.now().month.toString() == "JULY"){
                monthNum = "7"
            }
            else if(LocalDate.now().month.toString() == "AUGUST"){
                monthNum = "8"
            }
            else if(LocalDate.now().month.toString() == "SEPTEMBER"){
                monthNum = "9"
            }
            else if(LocalDate.now().month.toString() == "OCTOBER"){
                monthNum = "10"
            }
            else if(LocalDate.now().month.toString() == "NOVEMBER"){
                monthNum = "11"
            }
            else{
                monthNum = "12"
            }
            binding.tvHomeDate.text = LocalDate.now().year.toString()+"년 "+ monthNum +"월"

            itemList.add(ResponseWeekCalendarData(dayOfWeek.toString().substring(0, 3), i.toString()))
        }
        calendarList.adapter = listAdapter
    }
}
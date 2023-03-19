package com.example.coalba.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.R
import com.example.coalba.WorkspaceHomeActivity
import kotlinx.android.synthetic.main.item_day.view.*
import java.util.*

// 각 날짜를 표현하는 Grid 타입의 RecyclerView의 Adapter
class DayAdapter(val tempYear: Int, val tempMonth:Int, val dayList:MutableList<Date>):RecyclerView.Adapter<DayAdapter.DayView>() {
    val ROW = 6

    val calendar = Calendar.getInstance()
    //선택한 년월이 현재 날짜이면 현재 일 선택, 아니면 기본 1일 선택
    var selectedDay = if (tempYear == calendar.get(Calendar.YEAR) && tempMonth == calendar.get(Calendar.MONTH)) calendar.get(Calendar.DAY_OF_MONTH) else 1
    var preSelectedPos = 0 //이전에 선택한 날짜의 position (notifyDataSetChanged() 호출하지 않기 위해 사용)
    var selectedPos = 0 //현재 선택한 날짜의 position

    inner class DayView(val layout: View):RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {
        holder.layout.ll_day.setOnClickListener{
            preSelectedPos = selectedPos
            if (preSelectedPos != position) { //새로운 날짜를 선택했을 경우에만 업데이트
                selectedDay = dayList[position].date
                notifyItemChanged(preSelectedPos) //이전에 선택한 position 아이템에는 배경 표시 삭제
                notifyItemChanged(position) //이번에 선택한 position 아이템에는 배경 표시
            }
            (holder.layout.context as WorkspaceHomeActivity).dayClick(tempYear, tempMonth + 1, dayList[position].date)
        }
        holder.layout.tv_day.text = dayList[position].date.toString()
        // 파라미터로 받은 dayList를 이용하여 position%7의 값이 0일 경우 일요일로서 빨강색을, 6일 경우 토요일로서 파랑색의 스타일을 지정
        holder.layout.tv_day.setTextColor(when(position%7){
            0 -> Color.RED
            6 -> Color.BLUE
            else -> Color.BLACK
        })
        // 파라미터로 받은 tempMonth로 현재 월이 아닌 날짜의 경우 alpha를 낮추어 투명도를 줌으로써 현재 월의 날짜와 다르게 표시
        if(tempMonth != dayList[position].month){
            holder.layout.tv_day.alpha=0.4f
            // 현재 월이 아닌 경우에는 클릭 불가하도록
            holder.layout.isClickable = false
        }
        else {
            if (preSelectedPos == position) {
                holder.layout.tv_day.background = null //이전에 선택한 position 배경 표시 삭제
            }
            if (selectedDay == dayList[position].date) {
                holder.layout.tv_day.setBackgroundResource(R.drawable.bg_weekcalendar_choose) //현재 선택한 날짜 배경 표시
                selectedPos = position //현재 선택한 position 값 저장 (처음 화면에 들어왔을 때 오늘 날짜에 해당하는 position 모르기 때문에 여기서 selectedPos 변수값 세팅)
            }
        }
    }

    // R0W = 6주 * 7일로서 총 42개의 날짜가 표시
    override fun getItemCount(): Int {
        return ROW*7
    }
}
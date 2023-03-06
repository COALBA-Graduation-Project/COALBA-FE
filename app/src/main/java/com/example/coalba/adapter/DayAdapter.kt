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
class DayAdapter(val tempMonth:Int, val dayList:MutableList<Date>):RecyclerView.Adapter<DayAdapter.DayView>() {
    val ROW = 6

    inner class DayView(val layout: View):RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {
        holder.layout.ll_day.setOnClickListener{
            (holder.layout.context as WorkspaceHomeActivity).dayClick(dayList[position].date.toString())
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
        }
    }

    // R0W = 6주 * 7일로서 총 42개의 날짜가 표시
    override fun getItemCount(): Int {
        return ROW*7
    }
}
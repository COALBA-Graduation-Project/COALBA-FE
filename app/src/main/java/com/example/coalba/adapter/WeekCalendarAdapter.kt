package com.example.coalba.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.R
import com.example.coalba.data.response.WeekCalendarData
import com.example.coalba.databinding.ItemWeekcalendarBinding
import kotlinx.android.synthetic.main.item_weekcalendar.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import java.util.*

class WeekCalendarAdapter(private val wcList: List<WeekCalendarData>, private val homeDayClickListener: HomeDayClickListener) : RecyclerView.Adapter<WeekCalendarAdapter.WeekCalendarViewHolder>(){
    private var selectedDay = LocalDate.now().dayOfMonth

    class WeekCalendarViewHolder(private val binding: ItemWeekcalendarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:WeekCalendarData){
            binding.tvDate.text = item.date.toString()
            binding.tvDay.text = item.day

            if (item.status == "BEFORE"){
                binding.ivStatus.isVisible = true
            }
            else if (item.status == "COMPLETE") {
                binding.ivStatus.isVisible = true
                binding.ivStatus.setImageResource(R.drawable.bg_weekcalendar_complete)
            }
            else if (item.status == "INCOMPLETE") {
                binding.ivStatus.isVisible = true
                binding.ivStatus.setImageResource(R.drawable.bg_weekcalendar_incomplete)
            }
            else{
                binding.ivStatus.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekCalendarViewHolder {
        val binding = ItemWeekcalendarBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WeekCalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeekCalendarViewHolder, position: Int) {
        holder.bind(wcList[position])
        holder.itemView.setOnClickListener {
            selectedDay = wcList[position].date  // 클릭한 날짜 일 값 업데이트
            notifyDataSetChanged() //데이터 변경사항 notify 함으로써 클릭한 날짜 부분에 동그라미 포커싱? 효과 줌
            homeDayClickListener.click(wcList[position].year, wcList[position].month,wcList[position].date)
        }
        if (wcList[position].date == selectedDay) {//해당 날짜가 선택된 날짜일 경우
            holder.itemView.tv_date.setBackgroundResource(R.drawable.bg_weekcalendar_choose) //동그라미 포커싱
        } else {
            holder.itemView.tv_date.setBackgroundResource(R.drawable.bg_weekcalendar_unchoose)
        }
    }
    override fun getItemCount() = wcList.size

    interface HomeDayClickListener{
        fun click(year: Int, month: Int, day: Int)
    }
}
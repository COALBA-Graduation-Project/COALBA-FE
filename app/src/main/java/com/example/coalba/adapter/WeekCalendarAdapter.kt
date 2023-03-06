package com.example.coalba.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.R
import com.example.coalba.data.response.WeekCalendarData
import com.example.coalba.databinding.ItemWeekcalendarBinding
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import java.util.*

class WeekCalendarAdapter(private val wcList: List<WeekCalendarData>, private val homeDayClickListener: HomeDayClickListener) : RecyclerView.Adapter<WeekCalendarAdapter.WeekCalendarViewHolder>(){
    class WeekCalendarViewHolder(private val binding: ItemWeekcalendarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:WeekCalendarData){
            binding.tvDate.text = item.date.toString()
            binding.tvDay.text = item.day
            if (item.status == "BEFORE"){
                binding.ivStatus.isVisible = true
            }
            else if (item.status == "COMPLETE") {
                binding.ivStatus.isVisible = true
                binding.ivStatus.setImageResource(R.color.main)
            }
            else if (item.status == "INCOMPLETE") {
                binding.ivStatus.isVisible = true
                binding.ivStatus.setImageResource(R.color.refuse)
            }
            val today = binding.tvDate.text.toString()
            // 오늘 날짜
            val now = LocalDate.now().format(ofPattern("d"))

            // 오늘 날짜와 캘린더의 오늘 날짜가 같을 경우 background_blue 적용하기
            if (today == now) {
                binding.tvDate.setBackgroundResource(R.drawable.bg_weekcalendar_choose)
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
            homeDayClickListener.click(wcList[position].year, wcList[position].month,wcList[position].date)
        }
    }
    override fun getItemCount() = wcList.size

    interface HomeDayClickListener{
        fun click(year: Int, month: Int, day: Int)
    }
}
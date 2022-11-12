package com.example.coalba.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.R
import com.example.coalba.data.response.ScheduleData

class ScheduleAdapter(private val context: Context) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    var datas = mutableListOf<ScheduleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvName: TextView = itemView.findViewById(R.id.tv_schedule_name)
        private val tvTime: TextView = itemView.findViewById(R.id.tv_schedule_time)

        fun bind(item: ScheduleData){
            tvName.text = item.name
            tvTime.text = item.time
        }
    }
}
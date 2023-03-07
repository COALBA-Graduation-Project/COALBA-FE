package com.example.coalba.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.R
import com.example.coalba.data.response.WorkHistoryData

class WorkHistoryAdapter(private val context: Context) : RecyclerView.Adapter<WorkHistoryAdapter.ViewHolder>(){
    var datas = mutableListOf<WorkHistoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_workhistory,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: WorkHistoryAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvMonth: TextView = itemView.findViewById(R.id.tv_workhistory_month)
        private val tvHour: TextView = itemView.findViewById(R.id.tv_workhistory_hour)
        private val tvMinute: TextView = itemView.findViewById(R.id.tv_workhistory_minute)
        private val tvPay: TextView = itemView.findViewById(R.id.tv_workhistory_won)

        fun bind(item: WorkHistoryData){
            tvMonth.text = item.month
            tvHour.text = item.hour
            tvMinute.text = item.minute
            tvPay.text = item.pay
        }
    }
}
package com.example.coalba.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.R
import com.example.coalba.SubstituteRequestActivity
import com.example.coalba.data.response.ScheduleData
import com.example.coalba.databinding.ItemScheduleBinding

class ScheduleAdapter(private val context: Context) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    var datas = mutableListOf<ScheduleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ScheduleData){
            binding.tvScheduleName.text = item.name
            binding.tvScheduleStarttime.text = item.starttime
            binding.tvScheduleEndtime.text = item.endtime

            if (item.isMySchedule == true && item.status == "BEFORE_WORK"){
                binding.ivWorkspacehomeChange.visibility = View.VISIBLE
            }
            binding.ivWorkspacehomeChange.setOnClickListener {
                Intent(context, SubstituteRequestActivity::class.java).apply {
                    putExtra("ForSubstitute", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}
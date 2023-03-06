package com.example.coalba.adapter

import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.*
import com.example.coalba.data.response.HomeScheduleData

class HomeSchduleAdapter(private val context: Context) : RecyclerView.Adapter<HomeSchduleAdapter.ViewHolder>() {
    var datas = mutableListOf<HomeScheduleData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeSchduleAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: HomeSchduleAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtWorkname: TextView = itemView.findViewById(R.id.tv_home_schedule_workname)
        private val txtStarttime: TextView = itemView.findViewById(R.id.tv_home_schedule_starttime)
        private val txtEndtime: TextView = itemView.findViewById(R.id.tv_home_schedule_endtime)
        private val txtState: TextView = itemView.findViewById(R.id.tv_home_schedule_state)
        private val btnScheduleCome: Button = itemView.findViewById(R.id.btn_home_schedule_come)

        fun bind(item: HomeScheduleData){
            txtWorkname.text = item.workname
            txtStarttime.text = item.starttime
            txtEndtime.text = item.endtime
            txtState.text = item.state
            btnScheduleCome.setOnClickListener {
                (context as MainActivity).detectBeacon()
            }
        }
    }
}
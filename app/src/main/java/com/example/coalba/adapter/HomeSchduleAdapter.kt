package com.example.coalba.adapter

import android.content.Context
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.*
import com.example.coalba.data.response.HomeScheduleData
import com.example.coalba.databinding.ItemHomeScheduleBinding

class HomeSchduleAdapter(private val context: Context, private val startClickListener: StartClickListener, private val endClickListener: EndClickListener) : RecyclerView.Adapter<HomeSchduleAdapter.ViewHolder>() {
    var datas = mutableListOf<HomeScheduleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemHomeScheduleBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(val binding: ItemHomeScheduleBinding) : RecyclerView.ViewHolder(binding.root){
        private val comeBtn : Button = itemView.findViewById(R.id.btn_home_schedule_come)
        private val leaveBtn : Button = itemView.findViewById(R.id.btn_home_schedule_leave)
        fun bind(item: HomeScheduleData){
            with(binding){
                if (item.logicalStartTime == null){
                    tvHomeScheduleStarttime.text = item.starttime // 출근 전일 때에는 스케줄 시작 시간
                }else{
                    tvHomeScheduleStarttime.text = item.logicalStartTime
                    // 이미 출근했을 때는 출근 버튼 비활성화
                    comeBtn.setBackgroundResource(R.drawable.bg_notworkbtn)
                    if (item.logicalStartTime == item.starttime) tvHomeScheduleStarttime.setTextColor(ContextCompat.getColor(context, R.color.main)) //근무 중
                    else tvHomeScheduleStarttime.setTextColor(ContextCompat.getColor(context, R.color.refuse)) //지각
                }
                if (item.logicalEndTime == null){
                    tvHomeScheduleEndtime.text = item.endtime // 퇴근 전일 때에는 스케줄 종료 시간
                }else{
                    tvHomeScheduleEndtime.text = item.logicalEndTime
                    // 이미 퇴근했을 때는 퇴근 버튼 비활성화
                    leaveBtn.setBackgroundResource(R.drawable.bg_notworkbtn)
                    if (item.logicalEndTime == item.endtime) tvHomeScheduleEndtime.setTextColor(ContextCompat.getColor(context, R.color.main)) //정상 퇴근
                    else tvHomeScheduleEndtime.setTextColor(ContextCompat.getColor(context, R.color.refuse)) //조기 퇴근
                }
                tvHomeScheduleWorkname.text = item.workname
                when (item.state) {
                    "BEFORE_WORK" -> { tvHomeScheduleState.text = "근무전" }
                    "ON_DUTY" -> { tvHomeScheduleState.text = "근무중" }
                    "LATE" -> { tvHomeScheduleState.text = "지각" }
                    "SUCCESS " -> { tvHomeScheduleState.text = "근무 완료" }
                    "FAIL" -> { tvHomeScheduleState.text = "근무 실패" }
                }

                btnHomeScheduleCome.setOnClickListener {
                    // (context as MainActivity).detectBeacon()
                    startClickListener.click1(datas[position].scheduleId, position)
                }
                btnHomeScheduleLeave.setOnClickListener {
                    endClickListener.click2(datas[position].scheduleId, position)
                }
            }
        }
    }

    interface StartClickListener{
        fun click1(scheduleId : Long, position: Int)
    }

    interface EndClickListener{
        fun click2(scheduleId : Long, position: Int)
    }
}
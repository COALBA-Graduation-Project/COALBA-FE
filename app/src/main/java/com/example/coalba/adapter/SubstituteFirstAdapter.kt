package com.example.coalba.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba.R
import com.example.coalba.SubstituteDetailActivity
import com.example.coalba.SubstituteProcessActivity
import com.example.coalba.data.response.SubstituteFirstData
import com.example.coalba.databinding.ItemSubstituteFirstBinding

class SubstituteFirstAdapter : RecyclerView.Adapter<SubstituteFirstAdapter.ViewHolder>() {
    lateinit var items: MutableList<SubstituteFirstData>

    fun build(i: MutableList<SubstituteFirstData>): SubstituteFirstAdapter{
        items = i
        return this
    }
    inner class ViewHolder(val binding: ItemSubstituteFirstBinding): RecyclerView.ViewHolder(binding.root){
        val context = binding.root.context
        fun bind(item: SubstituteFirstData){
            with(binding){
                Glide.with(itemView).load(item.img).into(ivSubstituteProfile)
                tvSubstituteName.text = item.name
                tvSubstituteWorkname.text = item.workname
                tvSubstituteStarttime.text = item.starttime
                tvSubstituteEndtime.text = item.endtime
                when (item.status) {
                    "WAITING" -> {
                        tvSubstituteState.text = ""
                    }
                    "ACCEPTANCE" -> {
                        tvSubstituteState.text = "수락"
                        tvSubstituteState.setTextColor(ContextCompat.getColor(context, R.color.main))
                    }
                    "REFUSAL" -> {
                        tvSubstituteState.text = "거절"
                        tvSubstituteState.setTextColor(ContextCompat.getColor(context, R.color.refuse))
                    }
                    "APPROVAL" -> {
                        tvSubstituteState.text = "승인"
                        tvSubstituteState.setTextColor(ContextCompat.getColor(context, R.color.main))
                    }
                    "DISAPPROVAL" -> {
                        tvSubstituteState.text = "비승인"
                        tvSubstituteState.setTextColor(ContextCompat.getColor(context, R.color.refuse))
                    }
                    "CANCELLATION" -> {
                        tvSubstituteState.text = "취소"
                        tvSubstituteState.setTextColor(ContextCompat.getColor(context, R.color.gray))
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSubstituteFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            // 대기 상태일 때는 대타근무 처리 화면으로 이동
            if (items[position].status == "WAITING"){
                Intent(holder.itemView.context, SubstituteProcessActivity::class.java).apply {
                    putExtra("substitutereqId", items[position].substituteReqId)
                    putExtra("divide", 1)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { holder.itemView.context.startActivity(this) }
            }
            // 그외의 상태일 때는 대타근무 상세 화면으로 이동
            else {
                Intent(holder.itemView.context, SubstituteDetailActivity::class.java).apply {
                    putExtra("substitutereqId", items[position].substituteReqId)
                    putExtra("divide", items[position].status)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { holder.itemView.context.startActivity(this) }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
package com.example.coalba.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba.R
import com.example.coalba.data.response.SubstituteFirstData
import com.example.coalba.data.response.SubstituteSecondData
import com.example.coalba.databinding.ItemSubstituteFirstBinding
import com.example.coalba.databinding.ItemSubstituteSecondBinding

class SubstituteSecondAdapter: RecyclerView.Adapter<SubstituteSecondAdapter.ViewHolder>() {
    lateinit var items: MutableList<SubstituteSecondData>

    fun build(i: MutableList<SubstituteSecondData>): SubstituteSecondAdapter{
        items = i
        return this
    }
    inner class ViewHolder(val binding: ItemSubstituteSecondBinding): RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        fun bind(item: SubstituteSecondData){
            with(binding){
                Glide.with(itemView).load(item.img).into(ivSubstituteProfile)
                tvSubstituteName.text = item.name
                tvSubstituteWorkname.text = item.workname
                tvSubstituteStarttime.text = item.starttime
                tvSubstituteEndtime.text = item.endtime
                when (item.status) {
                    "WAITING" -> {
                        tvSubstituteState.text = ""
                        tvSubstituteState.setTextColor(ContextCompat.getColor(context, R.color.gray))
                    }
                    "ACCEPTANCE" -> {
                        tvSubstituteState.text = "수락"
                        tvSubstituteState.setTextColor(ContextCompat.getColor(context, R.color.main))
                    }
                    "REFUSAL" -> {
                        tvSubstituteState.text = "지각"
                        tvSubstituteState.setTextColor(ContextCompat.getColor(context, R.color.refuse))
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSubstituteSecondBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
package com.example.coalba.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.data.response.SubstituteFirstYearmonthData
import com.example.coalba.data.response.SubstituteSecondYearmonthData
import com.example.coalba.databinding.ItemSubstituteFirstYearmonthBinding
import com.example.coalba.databinding.ItemSubstituteSecondYearmonthBinding

class SubstituteSecondYearmonthAdapter: RecyclerView.Adapter<SubstituteSecondYearmonthAdapter.ViewHolder>() {
    lateinit var items: ArrayList<SubstituteSecondYearmonthData>

    fun build(i: ArrayList<SubstituteSecondYearmonthData>) : SubstituteSecondYearmonthAdapter{
        items = i
        return this
    }
    class ViewHolder(val binding: ItemSubstituteSecondYearmonthBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(item: SubstituteSecondYearmonthData){
            with(binding){
                tvSubstituteSecondYear.text = item.year
                tvSubstituteSecondMonth.text = item.month
                rvSubstituteSecond.apply {
                    adapter = SubstituteSecondAdapter().build(item.substituteReqList)
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSubstituteSecondYearmonthBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    override fun getItemCount(): Int = items.size
}
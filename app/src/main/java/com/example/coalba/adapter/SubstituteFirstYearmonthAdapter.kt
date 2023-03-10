package com.example.coalba.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.data.response.SubstituteFirstYearmonthData
import com.example.coalba.databinding.ItemSubstituteFirstYearmonthBinding

class SubstituteFirstYearmonthAdapter: RecyclerView.Adapter<SubstituteFirstYearmonthAdapter.ViewHolder>() {
    lateinit var items: ArrayList<SubstituteFirstYearmonthData>

    fun build(i: ArrayList<SubstituteFirstYearmonthData>) : SubstituteFirstYearmonthAdapter{
        items = i
        return this
    }
    class ViewHolder(val binding:ItemSubstituteFirstYearmonthBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(item: SubstituteFirstYearmonthData){
            with(binding){
                tvSubstituteFirstYear.text = item.year
                tvSubstituteFirstMonth.text = item.month
                rvSubstituteFirst.apply {
                    adapter = SubstituteFirstAdapter().build(item.substituteReqList)
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSubstituteFirstYearmonthBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    override fun getItemCount(): Int = items.size
}
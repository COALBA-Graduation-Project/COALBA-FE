package com.example.coalba.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba.R
import com.example.coalba.SubstituteDetailActivity
import com.example.coalba.data.response.SubstituteData

class SubstituteAdapter(private val context: Context) : RecyclerView.Adapter<SubstituteAdapter.ViewHolder>() {
    var datas = mutableListOf<SubstituteData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubstituteAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_substitute, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: SubstituteAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val imgProfile: ImageView = itemView.findViewById(R.id.iv_substitute_profile)
        private val txtName: TextView = itemView.findViewById(R.id.tv_substitute_name)
        private val txtWorkname: TextView = itemView.findViewById(R.id.tv_substitute_workname)
        private val txtDate: TextView = itemView.findViewById(R.id.tv_substitute_date)
        private val txtStarttime: TextView = itemView.findViewById(R.id.tv_substitute_starttime)
        private val txtEndtime: TextView = itemView.findViewById(R.id.tv_substitute_endtime)
        private val txtState: TextView = itemView.findViewById(R.id.tv_substitute_state)

        fun bind(item: SubstituteData){
            Glide.with(itemView).load(item.img).into(imgProfile)
            txtName.text = item.name
            txtWorkname.text = item.workname
            txtDate.text = item.date
            txtStarttime.text = item.starttime
            txtEndtime.text = item.endtime
            txtState.text = item.state

            itemView.setOnClickListener {
                Intent(context, SubstituteDetailActivity::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }

        }
    }
}
package com.example.coalba.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coalba.R
import com.example.coalba.data.response.SubstituteAddPersonData

class SubstituteAddAdapter(private val context: Context, private val possiblePersonListener: PossiblePersonListener): RecyclerView.Adapter<SubstituteAddAdapter.ViewHolder>() {
    var datas = mutableListOf<SubstituteAddPersonData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_substitute_putperson, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.itemView.setOnClickListener {
            possiblePersonListener.click(datas[position].staffId, datas[position].name)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val imgProfile: ImageView = itemView.findViewById(R.id.iv_substitute_putperson)
        private val txtName: TextView = itemView.findViewById(R.id.tv_substitute_putperson_name)

        fun bind(item: SubstituteAddPersonData) {
            Glide.with(itemView).load(item.img).into(imgProfile)
            txtName.text = item.name
        }
    }
    interface PossiblePersonListener{
        fun click(staffId: Long, name: String)
    }
}
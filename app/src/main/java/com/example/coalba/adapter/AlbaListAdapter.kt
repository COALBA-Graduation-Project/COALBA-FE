package com.example.coalba.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.R
import com.example.coalba.WorkspaceHomeActivity
import com.example.coalba.data.response.AlbalistData

class AlbaListAdapter(private val context: Context): RecyclerView.Adapter<AlbaListAdapter.ViewHolder>() {
    var datas = mutableListOf<AlbalistData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbaListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_albalist,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: AlbaListAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtName: TextView = itemView.findViewById(R.id.tv_albalist_name)

        fun bind(item:AlbalistData){
            txtName.text = item.name

            itemView.setOnClickListener {
                Intent(context, WorkspaceHomeActivity::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}
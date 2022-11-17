package com.example.coalba.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.R
import com.example.coalba.data.response.MessageData

class MessageAdapter(private val context: Context): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    var datas = mutableListOf<MessageData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_message,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtType: TextView = itemView.findViewById(R.id.tv_message_title)
        private val txtDate: TextView = itemView.findViewById(R.id.tv_message_date)
        private val txtTime: TextView = itemView.findViewById(R.id.tv_message_time)
        private val txtContent: TextView = itemView.findViewById(R.id.tv_message_content)

        fun bind(item: MessageData){
            txtType.text = item.type
            txtDate.text = item.date
            txtTime.text = item.time
            txtContent.text = item.content
        }
    }
}
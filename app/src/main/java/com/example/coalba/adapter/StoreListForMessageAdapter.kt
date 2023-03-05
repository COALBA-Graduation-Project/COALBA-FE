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
import com.example.coalba.MessageDetailActivity
import com.example.coalba.R
import com.example.coalba.data.response.StoreListForMessageData

class StoreListForMessageAdapter(private val context: Context) : RecyclerView.Adapter<StoreListForMessageAdapter.ViewHolder>() {
    var datas = mutableListOf<StoreListForMessageData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_storelist_for_message,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val img: ImageView = itemView.findViewById(R.id.iv_storelist_for_message)
        private val txtName: TextView = itemView.findViewById(R.id.tv_storelist_name_for_message)

        fun bind(item:StoreListForMessageData){
            Glide.with(itemView).load(item.img).into(img)
            txtName.text = item.name

            itemView.setOnClickListener {
                Intent(context, MessageDetailActivity::class.java).apply {
                    putExtra("dataForMessage", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}
package com.example.coalba.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.coalba.R
import com.example.coalba.adapter.AlbaListAdapter
import com.example.coalba.data.response.AlbalistData
import com.example.coalba.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private lateinit var binding:FragmentMessageBinding
    lateinit var albaListAdapter: AlbaListAdapter
    val datas = mutableListOf<AlbalistData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        // initRecycler() => todo: MessageFragment용 adapter 하나 더 만들어야함!!!
        return binding.root
    }

    /*
    private fun initRecycler(){
        albaListAdapter = AlbaListAdapter(requireContext())
        binding.rvAlbalist.adapter = albaListAdapter

        datas.apply{
            add(AlbalistData(name = "송이커피 숙대점"))
            add(AlbalistData(name = "송이마라탕 숙대점"))
            add(AlbalistData(name = "송송마카롱 숙대점"))

            albaListAdapter.datas=datas
            albaListAdapter.notifyDataSetChanged()
        }
    }*/
}
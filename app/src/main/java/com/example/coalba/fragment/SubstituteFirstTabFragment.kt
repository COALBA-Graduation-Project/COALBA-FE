package com.example.coalba.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba.R
import com.example.coalba.adapter.AlbaListAdapter
import com.example.coalba.adapter.SubstituteAdapter
import com.example.coalba.data.response.AlbalistData
import com.example.coalba.data.response.SubstituteData
import com.example.coalba.databinding.FragmentSubstituteFirstTabBinding

class SubstituteFirstTabFragment : Fragment() {
    private var _binding: FragmentSubstituteFirstTabBinding? = null
    private val binding get() = _binding !!

    lateinit var substituteAdapter: SubstituteAdapter
    val datas = mutableListOf<SubstituteData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubstituteFirstTabBinding.inflate(inflater, container, false)
        initRecycler()
        return binding.root
    }
    private fun initRecycler(){
        substituteAdapter = SubstituteAdapter(requireContext())
        binding.rvSubstitute.adapter = substituteAdapter

        datas.apply{
            add(SubstituteData(img = R.drawable.girl, name = "조예진", workname = "송이커피 숙대점", date = "10/30", starttime = "16:00", endtime = "21:00", state = "수락"))
            add(SubstituteData(img = R.drawable.girl, name = "신지연", workname = "송이커피 숙대점", date = "10/26", starttime = "12:00", endtime = "18:00", state = "수락"))
            add(SubstituteData(img = R.drawable.girl, name = "김다은", workname = "송이마라탕 숙대점", date = "9/30", starttime = "14:00", endtime = "20:00", state = "수락"))
            add(SubstituteData(img = R.drawable.girl, name = "송송", workname = "송이마라탕 숙대점", date = "9/23", starttime = "10:00", endtime = "14:00", state = "수락"))

            substituteAdapter.datas=datas
            substituteAdapter.notifyDataSetChanged()
        }
    }
}
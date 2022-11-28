package com.example.coalba.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba.R
import com.example.coalba.adapter.SubstituteAdapter
import com.example.coalba.data.response.SubstituteData
import com.example.coalba.databinding.FragmentSubstituteSecondTabBinding

class SubstituteSecondTabFragment : Fragment() {
    private var _binding: FragmentSubstituteSecondTabBinding? = null
    private val binding get() = _binding !!

    lateinit var substituteAdapter: SubstituteAdapter
    val datas = mutableListOf<SubstituteData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubstituteSecondTabBinding.inflate(inflater, container, false)
        initRecycler()
        return binding.root
    }
    private fun initRecycler(){
        substituteAdapter = SubstituteAdapter(requireContext())
        binding.rvSubstitute.adapter = substituteAdapter

        datas.apply{
            add(SubstituteData(img = R.drawable.girl, name = "김다은", workname = "송이마라탕 숙대점", date = "11/03", starttime = "12:00", endtime = "20:00", state = "수락"))
            add(SubstituteData(img = R.drawable.girl, name = "조예진", workname = "송이커피 숙대점", date = "10/28", starttime = "15:00", endtime = "19:00", state = "수락"))
            add(SubstituteData(img = R.drawable.girl, name = "신지연", workname = "송이커피 숙대점", date = "10/10", starttime = "18:00", endtime = "22:00", state = "수락"))
            add(SubstituteData(img = R.drawable.girl, name = "알바송", workname = "송이마라탕 숙대점", date = "9/29", starttime = "13:00", endtime = "19:00", state = "수락"))

            substituteAdapter.datas=datas
            substituteAdapter.notifyDataSetChanged()
        }
    }
}
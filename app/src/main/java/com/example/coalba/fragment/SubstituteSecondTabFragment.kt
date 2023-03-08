package com.example.coalba.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coalba.R
import com.example.coalba.adapter.SubstituteFirstYearmonthAdapter
import com.example.coalba.adapter.SubstituteSecondYearmonthAdapter
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.*
import com.example.coalba.databinding.FragmentSubstituteSecondTabBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubstituteSecondTabFragment : Fragment() {
    private var _binding: FragmentSubstituteSecondTabBinding? = null
    private val binding get() = _binding !!
    private var substituteSecondList = ArrayList<SubstituteSecondYearmonthData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSubstituteSecondTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 내가 받은 대타근무 요청 관리 리스트 조회(to. 나) 서버 연동
        RetrofitManager.substituteReqService?.substituteTo()?.enqueue(object:
            Callback<SubstituteToResponseData> {
            override fun onResponse(
                call: Call<SubstituteToResponseData>,
                response: Response<SubstituteToResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("SubstituteTo", "success")
                    val data = response.body()
                    Log.d("SubstituteToData", data.toString())

                    substituteSecondList.addAll(
                        data!!.totalSubstituteReqList.map {yearmonth ->
                            SubstituteSecondYearmonthData(yearmonth.year.toString(), yearmonth.month.toString(),
                                yearmonth.substituteReqList.map { datas ->
                                    SubstituteSecondData(datas.substituteReqId, datas.senderImageUrl, datas.senderName, datas.workspaceName, datas.startDateTime, datas.endDateTime, datas.status)
                                }.toMutableList())
                        }
                    )
                    binding.rvSubstituteSecondYearmonth.apply {
                        adapter = SubstituteSecondYearmonthAdapter().build(substituteSecondList)
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                    }

                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("SubstituteTo", "fail")
                }
            }
            override fun onFailure(call: Call<SubstituteToResponseData>, t: Throwable) {
                Log.d("SubstituteTo", "error")
            }
        })
    }
}
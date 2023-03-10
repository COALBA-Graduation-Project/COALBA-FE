package com.example.coalba.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coalba.adapter.SubstituteFirstYearmonthAdapter
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.SubstituteFirstData
import com.example.coalba.data.response.SubstituteFirstYearmonthData
import com.example.coalba.data.response.SubstituteFromResponseData
import com.example.coalba.databinding.FragmentSubstituteFirstTabBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubstituteFirstTabFragment : Fragment() {
    private var _binding: FragmentSubstituteFirstTabBinding? = null
    private val binding get() = _binding !!
    private var substituteFirstList = ArrayList<SubstituteFirstYearmonthData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSubstituteFirstTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 내가 보낸 대타근무 요청 관리 리스트 조회(from. 나) 서버 연동
        RetrofitManager.substituteReqService?.substituteFrom()?.enqueue(object:
            Callback<SubstituteFromResponseData> {
            override fun onResponse(
                call: Call<SubstituteFromResponseData>,
                response: Response<SubstituteFromResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("SubstituteFrom", "success")
                    val data = response.body()
                    Log.d("SubstituteFromData", data.toString())

                    substituteFirstList.addAll(
                        data!!.totalSubstituteReqList.map {yearmonth ->
                            SubstituteFirstYearmonthData(yearmonth.year.toString(), yearmonth.month.toString(),
                            yearmonth.substituteReqList.map { datas ->
                                SubstituteFirstData(datas.substituteReqId, datas.receiverImageUrl, datas.receiverName, datas.workspaceName, datas.startDateTime, datas.endDateTime, datas.status)
                            }.toMutableList())
                        }
                    )
                    binding.rvSubstituteFirstYearmonth.apply {
                        adapter = SubstituteFirstYearmonthAdapter().build(substituteFirstList)
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                    }

                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("SubstituteFrom", "fail")
                }
            }
            override fun onFailure(call: Call<SubstituteFromResponseData>, t: Throwable) {
                Log.d("SubstituteFrom", "error")
            }
        })
    }
}
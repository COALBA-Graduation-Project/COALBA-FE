package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.coalba.adapter.WorkHistoryAdapter
import com.example.coalba.data.response.WorkHistoryData
import com.example.coalba.databinding.ActivityMainBinding
import com.example.coalba.databinding.ActivityWorkHistoryBinding

class WorkHistoryActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityWorkHistoryBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    lateinit var workhistoryAdapter: WorkHistoryAdapter
    val datas = mutableListOf<WorkHistoryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityWorkHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create an ArrayAdapter
        val adapter = ArrayAdapter.createFromResource(this, R.array.workhistory_list, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.spinWorkhistory.adapter = adapter

        initRecycler()
    }
    private fun initRecycler(){
        workhistoryAdapter = WorkHistoryAdapter(this)
        binding.rvWorkhistory.adapter = workhistoryAdapter

        datas.apply {
            add(WorkHistoryData(month = "11", hour="28", minute = "30"))
            add(WorkHistoryData(month = "10", hour="32", minute = "20"))
            add(WorkHistoryData(month = "09", hour="35", minute = "00"))
            add(WorkHistoryData(month = "08", hour="29", minute = "40"))
            workhistoryAdapter.datas = datas
            workhistoryAdapter.notifyDataSetChanged()
        }
    }
    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}
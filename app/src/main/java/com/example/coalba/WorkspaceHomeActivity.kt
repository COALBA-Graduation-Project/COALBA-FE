package com.example.coalba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.coalba.adapter.MonthAdapter
import com.example.coalba.adapter.ScheduleAdapter
import com.example.coalba.data.response.AlbalistData
import com.example.coalba.data.response.ScheduleData
import com.example.coalba.databinding.ActivityWorkspaceHomeBinding

class WorkspaceHomeActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityWorkspaceHomeBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    lateinit var scheduleAdapter: ScheduleAdapter
    val datas = mutableListOf<ScheduleData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityWorkspaceHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // rv_workspacehome 리사이클러뷰는 각 월을 나타낼 리스트로서 가로로 전환하기 위하여 LinearLayoutManager의 Horizontal 속성을 준다
        val monthListManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val monthListAdapter = MonthAdapter()

        binding.rvWorkspacehome.apply {
            layoutManager = monthListManager
            adapter = monthListAdapter
            // scrollToPosition은 리스트를 item의 위치를 지정한 곳에서 시작. 해당 위치에서 리스트를 시작하는 이유는 Adapter부분에서 설명
            scrollToPosition(Int.MAX_VALUE/2)
        }
        // PagerSnapHelper()를 설정함으로써 한 항목씩 스크롤이 되도록 만들 수 있다.
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.rvWorkspacehome)
        initRecycler()

        val data = intent.getParcelableExtra<AlbalistData>("data")
        binding.tvWorkspacehome.text = data!!.name

        binding.ivWorkspacehomeChange.setOnClickListener {
            val intent = Intent(this, SubstituteRequestActivity::class.java)
            startActivity(intent)
        }
        binding.ivWorkspacehomeBack.setOnClickListener {
            finish()
        }
        binding.ivWorkspacehomeMessagebox.setOnClickListener {
            val intent = Intent(this, MessageDetailActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initRecycler(){
        scheduleAdapter = ScheduleAdapter(this)
        binding.rvSchedule.adapter = scheduleAdapter

        datas.apply {
            add(ScheduleData(name = "신지연", time = "14:00-19:00"))
            add(ScheduleData(name = "조예진", time = "15:00-21:00"))
            add(ScheduleData(name = "김다은", time = "11:00-13:30"))
            scheduleAdapter.datas = datas
            scheduleAdapter.notifyDataSetChanged()

        }
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}
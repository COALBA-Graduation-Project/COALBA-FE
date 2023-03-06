package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coalba.data.response.AlbalistData
import com.example.coalba.data.response.ScheduleData
import com.example.coalba.databinding.ActivitySubstituteRequestBinding
import com.example.coalba.databinding.ActivityWorkspaceHomeBinding

class SubstituteRequestActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivitySubstituteRequestBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var sId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivitySubstituteRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ScheduleData>("ForSubstitute")
        sId = data!!.scheduleId
        Log.d("scheduleId 출력", sId.toString())
        binding.ivSubstituteRequestBack.setOnClickListener {
            finish()
        }
    }
}
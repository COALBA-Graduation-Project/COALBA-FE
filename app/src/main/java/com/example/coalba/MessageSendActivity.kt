package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coalba.databinding.ActivityMessageDetailBinding
import com.example.coalba.databinding.ActivityMessageSendBinding

class MessageSendActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMessageSendBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMessageSendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivMessageBack.setOnClickListener {
            finish()
        }
    }
}
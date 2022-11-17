package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coalba.adapter.MessageAdapter
import com.example.coalba.data.response.MessageData
import com.example.coalba.databinding.ActivityMessageDetailBinding

class MessageDetailActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMessageDetailBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var messageAdapter: MessageAdapter
    val datas = mutableListOf<MessageData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMessageDetailBinding.inflate(layoutInflater)
        initRecycler()
        setContentView(binding.root)
    }
    private fun initRecycler(){
        messageAdapter = MessageAdapter(this)
        binding.rvMessage.adapter = messageAdapter

        datas.apply{
            add(MessageData(type = "보낸 쪽지", date = "09/29", time="11:10", content="넵 알겠습니다. 감사합니다~~!!!"))
            add(MessageData(type = "받은 쪽지", date = "09/28", time="15:30", content="알겠습니다. 그러면 스케줄표에 반영하도록 하겠습니다. 그래도 다음주 목요일에는 꼭 나와주세요!"))
            add(MessageData(type = "보낸 쪽지", date = "09/28", time="13:10", content="안녕하세요~! 저 조예진입니다. 다음주 목요일 14:00 ~ 21:30에 시험이 있어서 알바하기가 어려울 것 같습니다. 그래서 지연이가 대타해주겠다고 했는데 괜찮을까요?"))

            messageAdapter.datas=datas
            messageAdapter.notifyDataSetChanged()
        }
    }
}
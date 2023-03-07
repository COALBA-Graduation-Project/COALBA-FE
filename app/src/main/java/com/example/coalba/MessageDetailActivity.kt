package com.example.coalba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.coalba.adapter.MessageAdapter
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.data.response.AlbalistData
import com.example.coalba.data.response.MessageData
import com.example.coalba.data.response.MessagesResponseData
import com.example.coalba.data.response.StoreListForMessageData
import com.example.coalba.databinding.ActivityMessageDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageDetailActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMessageDetailBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var messageAdapter: MessageAdapter
    val datas = mutableListOf<MessageData>()
    var storeId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMessageDetailBinding.inflate(layoutInflater)
        messageAdapter = MessageAdapter(this)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<StoreListForMessageData>("dataForMessage")
        binding.tvMessageName.text = data!!.name
        storeId = data.workspaceId

        // 해당 워크스페이스 쪽지함 내 메시지 리스트 조회 (최신순)
        RetrofitManager.messageService?.messages(storeId)?.enqueue(object:
            Callback<MessagesResponseData> {
            override fun onResponse(
                call: Call<MessagesResponseData>,
                response: Response<MessagesResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("Messages", "success")
                    val data2 = response.body()
                    val num = data2!!.messageList.count()
                    if(num == 0){
                        Toast.makeText(this@MessageDetailActivity, "메시지가 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.rvMessage.adapter = messageAdapter
                        for(i in 0..num-1){
                            val itemdata = response.body()?.messageList?.get(i)
                            datas.add(MessageData(type= itemdata!!.sendingOrReceiving, date = itemdata.createDate, content = itemdata.content))
                        }
                        messageAdapter.datas=datas
                        messageAdapter.notifyDataSetChanged()
                    }
                }else{ // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Messages", "fail")
                }
            }
            override fun onFailure(call: Call<MessagesResponseData>, t: Throwable) {
                Log.d("Messages", "error")
            }
        })

        binding.ivMessageBack.setOnClickListener {
            finish()
        }
        binding.ivMessage.setOnClickListener {
            val intent = Intent(this, MessageSendActivity::class.java)
            intent.putExtra("workspaceID", storeId)
            startActivity(intent)
        }
    }
}
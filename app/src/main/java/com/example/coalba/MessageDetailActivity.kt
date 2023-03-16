package com.example.coalba

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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

    /*
    // 쪽지보내기 버튼 클릭 시 MessageSendActivity 화면 시작하고 MessageSendActivity finish 후 결과값 받아와서 처리
    // 이전 onAcitivityResult 역할과 비슷, 해당 메소드 deprecated 되어서 대신 사용
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                val responseData = it.data!!.getParcelableExtra<MessagesResponseData>("responseData")
                //responseData는 워크스페이스 등록 api 호출 후 응답 데이터
                Log.d("responseData =", responseData.toString())
                datas.clear()
                responseData!!.messageList.forEach { message ->
                    datas.add(MessageData(message.sendingOrReceiving, message.createDate, message.content))
                }
                binding.rvMessage.adapter?.notifyItemRangeChanged(0, responseData.messageList.count())
                //adapter에게 데이터 변경되었다는 것 알림
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMessageDetailBinding.inflate(layoutInflater)
        messageAdapter = MessageAdapter(this)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<StoreListForMessageData>("dataForMessage")
        binding.tvMessageName.text = data!!.name
        storeId = data.workspaceId

        binding.ivMessageBack.setOnClickListener {
            finish()
        }
        binding.ivMessage.setOnClickListener {
            val intent = Intent(this, MessageSendActivity::class.java)
            intent.putExtra("workspaceID", storeId)
            startActivity(intent)
            //startForResult.launch(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        datas.clear()
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
    }
}
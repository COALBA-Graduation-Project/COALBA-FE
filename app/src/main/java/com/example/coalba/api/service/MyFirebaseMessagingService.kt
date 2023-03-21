package com.example.coalba.api.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.coalba.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    //앱에 처음 접속했을 때 firebase에 기기가 등록되면 딱 한번만 호출
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println("MyFirebaseMessagingService.onNewToken") //확인용 print
    }

    //수신한 메시지 처리
    //→ Foreground 상태에서 Notification, Data Message 처리
    //→ Background 상태에서 Data Message만 처리, Notification Message는 시스템 트레이를 통해 자동으로 알림 표시
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        //안드로이드 '설정 → 알림 → 앱 선택 → 알림 카테고리'에서 앱의 알림 채널 확인 가능
        val CHANNEL_ID = "1" //채널 고유 아이디, 각 채널은 아이디로 식별하며 동일한 채널 아이디를 가지는 채널은 중복 생성X
        val CHANNEL_NAME = "일반 알림" //해당 채널 이름
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        var builder: NotificationCompat.Builder
        val title = message.notification?.title //수신한 알림 메시지 제목
        val body = message.notification?.body //수신한 알림 메시지 내용

        //API26부터 알림에 채널 설정 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel) //기존 채널 아이디와 동일한 채널이라면 실제로 생성X
            builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        } else {
            builder = NotificationCompat.Builder(applicationContext)
        }

        val notification = builder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.logo)
            .build()
        notificationManager.notify(1, notification) //알림 표시
        //첫번째 인자로는 0을 제외한 정수, 각 알림 식별 → 이 경우 생성되는 알림의 아이디 값이 모두 같기 때문에 알림이 쌓이지 않고 변경 내용만 반영됨
    }
}
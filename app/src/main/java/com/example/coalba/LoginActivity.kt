package com.example.coalba

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.coalba.api.jwt.CoalbaApplication
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.api.service.GoogleLoginService
import com.example.coalba.data.request.AuthRequestData
import com.example.coalba.data.request.GoogleLoginRequestData
import com.example.coalba.data.request.NotificationRequestData
import com.example.coalba.data.response.AuthResponseData
import com.example.coalba.data.response.GoogleLoginResponseData
import com.example.coalba.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityLoginBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var authResultLauncher: ActivityResultLauncher<Intent>  // 액티비티에서 데이터를 받아오기 위해

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (CoalbaApplication.prefs.accessToken != null && CoalbaApplication.prefs.refreshToken != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            finish()
            startActivity(intent)
        }

        // 바인딩
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 구글 로그인
        initForGoogleLogin()
        binding.googleLoginbtn.setOnClickListener { googleLogin() }
    }

    private fun initForGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope("https://www.googleapis.com/auth/calendar")) //기본 email, profile, openid + calendar 권한 요청
            //forceCodeForRefreshToken: 로그인 시마다 refresh token 발급 여부 (로그인 시마다 사용자에게 동의 얻어야 하는 불편O)
            .requestServerAuthCode(BuildConfig.GOOGLE_CLIENT_ID, true)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        authResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val serverAuthCode = task.getResult(ApiException::class.java).serverAuthCode //code 발급
                    /**
                     * 1. 발급된 code -> google access_token, refresh_token 발급
                     * 2. 백 서버 로그인 api 요청 with google access_token, refresh_token
                     * 3. 백 서버로부터 발급받은 access_token, refresh_token 내부 저장소에 저장
                     * => 백 서버에 대한 모든 api 요청 헤더에 access_token 추가됨 by AuthInterceptor
                     */
                    processServerLogin(serverAuthCode)
                } catch (e: ApiException) {
                    Log.e("signin", "failure")
                    Log.e("task", "error", e)
                }
            }
    }

    private fun processServerLogin(serverAuthCode: String?) {
        //GoogleLoginService: google access_token, refresh_token 발급 요청 위한 service
        GoogleLoginService.instance.getToken(
            GoogleLoginRequestData(
                "authorization_code",
                "",
                BuildConfig.GOOGLE_CLIENT_ID,
                BuildConfig.GOOGLE_CLIENT_SECRET,
                serverAuthCode.orEmpty()
            )
        ).enqueue(object : Callback<GoogleLoginResponseData> {
            override fun onResponse(
                call: Call<GoogleLoginResponseData>,
                response: Response<GoogleLoginResponseData>
            ) {
                if (response.isSuccessful) { //성공 시
                    Log.d("Google Login", "onResponse: ${response.body()}")
                    //아래 코드 없으면 다음 로그인 시 자동 로그인 됨, 즉 다른 계정으로 로그인하고 싶어도 이전에 로그인했던 계정으로 자동 로그인
                    googleSignInClient.signOut()
                    login("GOOGLE", response.body()?.accessToken, response.body()?.refreshToken) //백 서버 로그인
                } else { //실패 시
                    Log.e("Google Login", "onResponseFail: ${response.code()}")
                }
            }
            //실패 시
            override fun onFailure(call: Call<GoogleLoginResponseData>, t: Throwable) {
                Log.e("Google Login", "onFailure: ${t.fillInStackTrace()}")
            }
        })
    }

    private fun googleLogin() {
        val signInIntent = googleSignInClient.signInIntent
        authResultLauncher.launch(signInIntent)
    }

    // 처음 로그인해서 백 서버에게 토큰 값을 받아야 할 경우
    fun login(provider: String, accessToken: String?, refreshToken: String?) {
        RetrofitManager.authService?.login(provider, "STAFF", AuthRequestData(accessToken!!, refreshToken!!))
            ?.enqueue(object : Callback<AuthResponseData> {
                override fun onResponse(
                    call: Call<AuthResponseData>,
                    response: Response<AuthResponseData>
                ) {
                    if (response.isSuccessful) { //성공 시
                        val result = response.body()

                        // 백 서버로부터 발급받은 token 내부 저장소에 저장
                        CoalbaApplication.prefs.accessToken = result?.accessToken
                        CoalbaApplication.prefs.refreshToken = result?.refreshToken

                        // 새로운 유저인 경우 프로필 등록 화면으로 가장 먼저 이동해야 함
                        val intent: Intent = if (result?.isNewUser == true) {
                            Intent(this@LoginActivity, ProfileRegisterActivity::class.java)
                        } else {
                            Intent(this@LoginActivity, MainActivity::class.java)
                        }
                        finish()
                        startActivity(intent)
                    } else { //실패 시
                        Log.e(ContentValues.TAG, "onResponse: ${response.code()}")
                        Log.d("server err", response.errorBody()?.string().toString())
                    }
                }
                //실패 시
                override fun onFailure(call: Call<AuthResponseData>, t: Throwable) {
                    Log.e(ContentValues.TAG, "RetrofitManager - onFailure() called / t: ${t.fillInStackTrace()}")
                }
            })
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            println("it = $it") //토큰 출력, it이 토큰값
            // Coalba 서버에 해당 디바이스 토큰 저장하는 서버 연동
            val notificationData = NotificationRequestData(deviceToken = it)
            RetrofitManager.notificationService?.notification(notificationData)?.enqueue(object:
                Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        Log.d("Notification", "success")
                    }else{
                        // 이곳은 에러 발생할 경우 실행됨
                        Log.d("Notification", "fail")
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("Notification", "error")
                }
            })
        }
    }
}
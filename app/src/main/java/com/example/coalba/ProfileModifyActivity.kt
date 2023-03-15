package com.example.coalba

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coalba.api.retrofit.RetrofitManager
import com.example.coalba.databinding.ActivityProfileModifyBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileModifyActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityProfileModifyBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    // 갤러리에서 가져온 프로필 이미지를 백 서버에게 보내주기 위함
    var imageFile : File? = null
    var imageWideUri: Uri? = null

    companion object {
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }
    // 이미지를 결과값으로 받는 변수
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == RESULT_OK){
            // 이미지를 받으면 ImageView에 적용
            val imageUri = result.data?.data
            imageWideUri = imageUri
            imageUri?.let{
                // 서버 업로드를 위해 파일 형태로 변환
                // imageFile = File(getRealPathFromURI(it))

                // 이미지를 불러온다
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .apply(RequestOptions().override(500,500))
                    .into(binding.ivProfile)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityProfileModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 프로필 수정 시 기존 프로필 조회 후 수정
        val prevImgUrl = intent.getStringExtra("prevImageUrl")
        Glide.with(this).load(prevImgUrl).into(binding.ivProfile)

        binding.ivRegisterCamera.setOnClickListener {
            selectGallery()
        }
        binding.ivProfileBack.setOnClickListener {
            finish()
        }
        // 프로필 수정 서버 연동
        binding.btnProfileFinish.setOnClickListener {
            Log.d("profileEdit", "시작")
            Log.d("datavalue", "multipart값=> " + imageWideUri)

            if (imageWideUri == null){
                Toast.makeText(this, "프로필 이미지를 추가해주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                imageFile = File(getRealPathFromURI(imageWideUri!!))
                // 서버로 보내기 위해 RequestBody객체로 변환
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
                val body =
                    MultipartBody.Part.createFormData("imageFile", imageFile!!.name, requestFile)
                // String 값에 "" 없애기
                val sendData = intent.getIntExtra("prevImageUrl", 0)
                val jsonObj = JSONObject()
                jsonObj.put("realName", binding.etProfileName.text)
                jsonObj.put("phoneNumber", binding.etProfilePhonenumber.text)
                jsonObj.put("birthDate", binding.etProfileBirth.text)
                jsonObj.put("prevImageUrl", sendData)
                val body2 = RequestBody.create("application/json".toMediaTypeOrNull(), jsonObj.toString())
                // 현재 사용자의 정보를 받아올 것을 명시
                // 서버 통신은 I/O 작업이므로 비동기적으로 받아올 Callback 내부 코드는 나중에 데이터를 받아오고 실행
                RetrofitManager.profileService?.profileEdit(body2,body)?.enqueue(object :
                    Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        // 네트워크 통신에 성공한 경우
                        if (response.isSuccessful) {
                            Log.d("ProfileEdit", "success")
                            finish()

                        }else { // 이곳은 에러 발생할 경우 실행됨
                            Log.d("ProfileEdit", "fail")
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("ProfileEdit", "error!")
                    }
                })
            }
        }
    }
    // 이미지 실제 경로 반환
    fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiaomi")){
            return uri.path!!
        }
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, proj, null,null,null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }
    // 갤러리를 부르는 메서드
    private fun selectGallery(){
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        // 권한 확인
        if(writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED){
            // 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQ_GALLERY)
        }else{
            // 권한이 있는 경우 갤러리 실행
            val intent = Intent(Intent.ACTION_PICK)
            // intent의 data와 type을 동시에 설정하는 메서드
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*"
            )
            imageResult.launch(intent)
        }
    }
}
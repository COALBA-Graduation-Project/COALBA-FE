package com.example.coalba

import android.Manifest
import android.bluetooth.*
import android.location.LocationManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.*
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.coalba.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    private lateinit var navController: NavController

    //===== 권한 요청용 변수 시작 =====
    private val locationManager: LocationManager by lazy {
        getSystemService(LocationManager::class.java)
    }
    private val bluetoothManager: BluetoothManager by lazy {
        getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        bluetoothManager.adapter
    }
    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) { } //활성화
        else if (it.resultCode == RESULT_CANCELED) { } //비활성화
    }
    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ -> }
    //===== 권한 요청용 변수 끝 =====

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController= Navigation.findNavController(this,R.id.main_nav_host_fragment)
        setupWithNavController(binding.bnv,navController)

        //===== 권한 요청 시작 =====
        //블루투스 지원 장비인지 확인
        if (bluetoothAdapter == null) {
            Log.e("MainActivity", "블루투스를 지원하지 않는 장비입니다.")
            finish()
        }
        //android 12부터 추가된 블루투스 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestMultiplePermissions.launch(arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION
            ))
        }
        //android 12 미만은 위치 권한만
        else {
            requestMultiplePermissions.launch(arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
        //===== 권한 요청 끝 =====
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}
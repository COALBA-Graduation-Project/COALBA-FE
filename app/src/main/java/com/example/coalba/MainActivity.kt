package com.example.coalba

import android.Manifest
import android.bluetooth.*
import android.content.Intent
import android.location.LocationManager
import android.os.*
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.*
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.coalba.databinding.ActivityMainBinding
import com.example.coalba.fragment.HomeFragment
import org.altbeacon.beacon.*

class MainActivity : AppCompatActivity(), InternalBeaconConsumer {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    private lateinit var navController: NavController

    //===== beacon 코드 시작 =====
    private var beaconManager: BeaconManager? = null
    private var beaconListener: HomeFragment.OnBeaconListener? = null
    //===== beacon 코드 끝 =====

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

        // 커스텀한 toolbar를 액션바로 사용
        setSupportActionBar(binding.tbMain)
        // toolbar 타이틀 제거
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setLogo(R.drawable.ic_logo)
        supportActionBar?.setDisplayUseLogoEnabled(true)

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

        //===== beacon 코드 시작 =====
        beaconManager = BeaconManager.getInstanceForApplication(this)
        //iBeacon용 Layout 설정 (설정해야지 해당 비콘 인식 가능)
        beaconManager!!.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
        //===== beacon 코드 끝 =====
    }

    // 액션버튼 메뉴 액션바에 집어넣기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    // 액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.notice -> {
                Toast.makeText(applicationContext, "알림 화면으로!", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // 액티비티가 파괴될 때..
    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
        //===== beacon 코드 시작 =====
        beaconManager!!.unbindInternal(this)
        //===== beacon 코드 끝 =====
    }

    fun detectBeacon(beaconListener: HomeFragment.OnBeaconListener) {
        this.beaconListener = beaconListener

        bluetoothAdapter?.let {
            if (!it.isEnabled) { //블루투스 비활성화 상태
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                activityResultLauncher.launch(intent)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            locationManager.let { //위치 GPS 비활성화 상태 (android 12 미만에는 GPS 필요X)
                if (!it.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    activityResultLauncher.launch(intent)
                }
            }
        }
        beaconManager!!.bindInternal(this) //→ onBeaconServiceConnect() 메소드 호출
    }

    //===== beacon 코드 시작 =====
    override fun onBeaconServiceConnect() {
        beaconManager!!.removeAllMonitorNotifiers()
        //비콘 모니터 관련 notifier 등록
        beaconManager!!.addMonitorNotifier(object : MonitorNotifier {
            //beacon 감지O
            override fun didEnterRegion(p0: Region?) { }

            //beacon 감지X
            override fun didExitRegion(p0: Region?) { }

            override fun didDetermineStateForRegion(p0: Int, p1: Region?) {
                //beacon 감지O
                if (p0 == MonitorNotifier.INSIDE) {
                    //Beacon 감지 시, 스케줄 시작 API 요청
                    beaconManager!!.unbindInternal(this@MainActivity) //Beacon 감지 중단
                    beaconListener!!.startSchedule()
                }
                //beacon 감지X
                else { }
            }
        })

        //Region에 해당하는 비콘 모니터링 시작 (각 문자, 숫자는 구매한 Beacon의 고유값 → 값 달라지면 비콘 인식 불가)
        beaconManager!!.startMonitoring(Region("beacon", Identifier.parse("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0"),
            Identifier.fromInt(40011), Identifier.fromInt(17819)))
    }
    //===== beacon 코드 끝 =====
}
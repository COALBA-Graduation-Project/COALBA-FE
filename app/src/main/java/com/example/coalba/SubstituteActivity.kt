package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.coalba.adapter.SubstituteTabAdapter
import com.example.coalba.databinding.ActivitySubstituteBinding
import com.example.coalba.fragment.SubstituteFirstTabFragment
import com.example.coalba.fragment.SubstituteSecondTabFragment
import com.google.android.material.tabs.TabLayoutMediator

class SubstituteActivity : AppCompatActivity() {
    lateinit var binding: ActivitySubstituteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubstituteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        binding.ivSubstituteBack.setOnClickListener {
            finish()
        }
    }

    private fun initViewPager() {
        var substituteTabAdapter = SubstituteTabAdapter(this)
        substituteTabAdapter.addFragment(SubstituteFirstTabFragment())
        substituteTabAdapter.addFragment(SubstituteSecondTabFragment())

        // Adapter 연결
        binding.vpSubstitute.apply {
            adapter = substituteTabAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        // ViewPager, Tablayout 연결
        TabLayoutMediator(binding.tlSubstitute, binding.vpSubstitute) { tab, position ->
            when (position) {
                0 -> tab.text = "내가 요청한 대타근무"
                1 -> tab.text = "나에게 요청된 대타근무"
            }
        }.attach()
    }
}
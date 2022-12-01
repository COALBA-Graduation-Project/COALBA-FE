package com.example.coalba.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba.R
import com.example.coalba.SubstituteActivity
import com.example.coalba.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentMypageBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvMypageManage2.setOnClickListener {
            val intent = Intent(requireContext(), SubstituteActivity::class.java)
            startActivity(intent)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
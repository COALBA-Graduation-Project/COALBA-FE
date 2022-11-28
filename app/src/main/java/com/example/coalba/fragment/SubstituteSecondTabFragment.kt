package com.example.coalba.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coalba.R
import com.example.coalba.databinding.FragmentSubstituteSecondTabBinding

class SubstituteSecondTabFragment : Fragment() {
    private var _binding: FragmentSubstituteSecondTabBinding? = null
    private val binding get() = _binding !!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubstituteSecondTabBinding.inflate(inflater, container, false)
        return binding.root
    }
}
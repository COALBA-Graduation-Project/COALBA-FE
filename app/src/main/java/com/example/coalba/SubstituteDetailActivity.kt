package com.example.coalba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.coalba.data.response.SubstituteData
import com.example.coalba.databinding.ActivitySubstituteDetailBinding

class SubstituteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubstituteDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubstituteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<SubstituteData>("data")
        Glide.with(this).load(data!!.img).into(binding.ivSubstituteDetailProfile)
        binding.tvSubstituteDetailName.text = data!!.name
        binding.tvSubstituteDetailState.text = data!!.state
        binding.tvSubstituteDetailPlace.text = data!!.workname
        binding.tvSubstituteDetailDate.text = data!!.date
        binding.tvSubstituteDetailStarttime.text = data!!.starttime
        binding.tvSubstituteDetailEndtime.text = data!!.endtime

        binding.ivSubstituteDetailBack.setOnClickListener {
            finish()
        }
    }
}
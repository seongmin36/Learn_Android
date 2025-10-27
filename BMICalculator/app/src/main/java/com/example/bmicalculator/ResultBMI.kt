package com.example.bmicalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculator.databinding.ResultBmiBinding
import com.example.bmicalculator.utils.BMIUtils

class ResultBMI : AppCompatActivity() {
    private lateinit var binding: ResultBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ResultBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bmi = intent.getDoubleExtra("bmiResult", 0.00)
        val result = BMIUtils.resultBMI(bmi)

        binding.bmiResult.text= result

        // BMI 상태에 따라 이미지 변경
        when {
            bmi < 18.5 -> binding.bmiImg.setImageResource(R.drawable.underweight)
            bmi in 18.5..24.99 -> binding.bmiImg.setImageResource(R.drawable.normal)
            bmi in 25.0..29.99 -> binding.bmiImg.setImageResource(R.drawable.overweight)
            bmi >= 30.0 -> binding.bmiImg.setImageResource(R.drawable.obese)
        }
    }

}
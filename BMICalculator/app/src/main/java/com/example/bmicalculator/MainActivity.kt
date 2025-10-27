package com.example.bmicalculator

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmicalculator.databinding.ActivityMainBinding
import com.example.bmicalculator.utils.BMIUtils

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // cm + 버튼 이벤트
        binding.btnPlusCm.setOnClickListener {
            increaseValue(binding.inputCm)
        }
        // cm - 버튼 이벤트
        binding.btnMinusCm.setOnClickListener {
            decreaseValue(binding.inputCm)
        }
        // kg + 버튼 이벤트
        binding.btnPlusKg.setOnClickListener {
            increaseValue(binding.inputKg)
        }
        // kg - 버튼 이벤트
        binding.btnMinusKg.setOnClickListener {
            decreaseValue(binding.inputKg)
        }
        // BMI 계산 결과 출력
        binding.bmiCalc.setOnClickListener {
            val bmi = BMIUtils.calculateBMI(binding.inputKg, binding.inputCm)

            // 인텐트 생성
            val intent = Intent(this, ResultBMI::class.java)
            // 데이터 전달 (putExtra)
            intent.putExtra("bmiResult", bmi)
            startActivity(intent)
        }
    }

    // 증가 함수
    private fun increaseValue(editText: EditText) {
        val current = editText.text.toString().toIntOrNull() ?: 0
        editText.setText((current + 1).toString())
    }

    // 감소 함수
    private fun decreaseValue(editText: EditText) {
        val current = editText.text.toString().toIntOrNull() ?: 0
        val newValue = if (current > 0) current - 1 else 0
        editText.setText(newValue.toString())
    }
}
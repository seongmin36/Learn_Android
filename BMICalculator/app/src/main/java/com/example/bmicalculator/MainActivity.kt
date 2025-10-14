package com.example.bmicalculator

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmicalculator.databinding.ActivityMainBinding

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
            resultBMI()
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

    // BMI 측정값
    private fun calculateBMI(weight: EditText, height: EditText): Double {
        val kg = weight.text.toString().toDoubleOrNull() ?: 0.00
        val cm = height.text.toString().toDoubleOrNull() ?: 0.00

        val m = cm / 100

        val bmi: Double = kg / (m * m)

        return bmi
    }

    // BMI 측정 결과
    private fun resultBMI() {
        val bmi = calculateBMI(binding.inputKg, binding.inputCm)
        binding.bmiNum.text = String.format("당신의 BMI는 %.2f", bmi)

        when {
            bmi == 0.00 -> {
                binding.bmiStatus.text = "입력 오류"
                binding.bmiEx.text = "키와 몸무게를 올바르게 입력해주세요."
            }

            bmi < 18.50 -> {
                binding.bmiStatus.text = "저체중"
                binding.bmiEx.text = "식사량을 늘리세요."
            }

            bmi in 18.5..24.99 -> {
                binding.bmiStatus.text = "정상"
                binding.bmiEx.text = "축하합니다. 현재 상태를 유지하세요."
            }

            bmi in 25.50..29.99 -> {
                binding.bmiStatus.text = "과체중"
                binding.bmiEx.text = "운동과 식사량을 조절하세요."
            }

            bmi >= 30.00 -> {
                binding.bmiStatus.text = "비만"
                binding.bmiEx.text = "위험한 단계입니다. 다이어트와 운동이 필요합니다."
            }
        }
    }
}
package com.example.bmicalculator.utils

import android.widget.EditText
import kotlin.compareTo

object BMIUtils {
    // BMI 계산 함수
    fun calculateBMI(weight: EditText, height: EditText): Double {
        val kg = weight.text.toString().toDoubleOrNull() ?: 0.00
        val cm = height.text.toString().toDoubleOrNull() ?: 0.00
        val m = cm / 100

        val bmi: Double = kg / (m * m)

        return bmi
    }

    // BMI 측정 결과
    fun resultBMI(bmi: Double): String {
        val bmiFormat = String.format("%.2f", bmi)
        return when {
            bmi == 0.00 -> {
                "BMI: $bmiFormat\n입력 오류\n키와 몸무게를 올바르게 입력해주세요."
            }

            bmi < 18.50 -> {
                "BMI: $bmiFormat\n결과: 저체중\n설명: 식사량을 늘리세요."
            }

            bmi in 18.5..24.99 -> {
                "BMI: $bmiFormat\n결과: 정상\n설명: 축하합니다. 현재 상태를 유지하세요."
            }

            bmi in 25.50..29.99 -> {
                "BMI: $bmiFormat\n결과: 과체중\n설명: 운동과 식사량을 조절하세요."
            }

            bmi >= 30.00 -> {
                "BMI: $bmiFormat\n결과: 비만\n설명: 위험한 단계입니다. 다이어트와 운동이 필요합니다."
            }

            else -> "에러"
        }
    }
}
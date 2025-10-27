package com.example.cal_date

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cal_date.databinding.ActivityMainBinding
import android.app.DatePickerDialog
import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectDateBtn.setOnClickListener {
            showDatePickerDialog()
        }

        binding.button2.setOnClickListener {
            calcDatePicker()
        }
    }

    private fun showDatePickerDialog() {
        val now = Calendar.getInstance()

        // DatePickerDialog 생성
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // 날짜 포맷 지정 (예: 2025-10-16)
                val formattedDate =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)

                // 버튼 텍스트에 선택한 날짜 표시
                binding.selectDateBtn.text = formattedDate
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH),
        )
        datePicker.show()
    }

    private fun calcDatePicker() {
        val daysPick = binding.date.text.toString()
        val datePick = binding.selectDateBtn.text.toString()

        if (daysPick.isBlank() || datePick == "날짜 선택") {
            binding.textView.text = "날짜와 일수를 모두 입력하세요."
            return
        }

        // 입력값 파싱
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val baseDate = sdf.parse(datePick)

        val cal = Calendar.getInstance()
        cal.time = baseDate!!         // 없으면 오늘 날짜로 계산됨
        cal.add(Calendar.DAY_OF_YEAR, daysPick.toInt())

        val result =sdf.format(cal.time)
        binding.textView.text="${datePick}로부터 ${daysPick}일 경과한 날짜는 ${result}입니다."
    }
}
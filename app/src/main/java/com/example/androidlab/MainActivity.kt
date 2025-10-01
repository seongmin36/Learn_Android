package com.example.androidlab

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var pauseTime = 0L

    var binding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(binding.root)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 버튼 상태
        updateButtonState(start = true, stop = false, reset = false)

        // 시작 버튼
        binding.startButton.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()

            updateButtonState(start = false, stop = true, reset = true)
        }

        // 일시 정지 버튼
        binding.stopButton.setOnClickListener {
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            updateButtonState(start = true, stop = false, reset = true)
        }

        // 초기화 버튼
        binding.resetButton.setOnClickListener {
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            Toast.makeText(this, "타이머 초기화!", Toast.LENGTH_SHORT).show()
            updateButtonState(start = true, stop = false, reset = false)
        }
    }

    // 버튼 상태 관리 함수
    private fun updateButtonState(start: Boolean, stop: Boolean, reset: Boolean) {
        binding.startButton.isEnabled = start
        binding.stopButton.isEnabled = stop
        binding.resetButton.isEnabled = reset
    }
}

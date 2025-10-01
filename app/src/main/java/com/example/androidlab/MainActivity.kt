package com.example.androidlab

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    var timer: CountDownTimer? = null
    var timeLeft: Long = 0L
    var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 버튼 상태
        updateButtonState(start = true, stop = false, reset = false)

        // 시작 버튼
        binding.startButton.setOnClickListener {
            val input = binding.inputTime.text.toString()
            if(input.isEmpty()) return@setOnClickListener

            if(!isPaused) {
                timeLeft = input.toLong() * 1000
            }

            // CountDown을 실행시켜 주는 android.os 내장 메서드
            timer = object : CountDownTimer(timeLeft, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft = millisUntilFinished
                    val totalSeconds = millisUntilFinished / 1000
                    val minutes = (totalSeconds / 60).toString().padStart(2, '0')
                    val seconds = (totalSeconds % 60).toString().padStart(2, '0')
                    binding.timerText.text = "$minutes:$seconds"
                }

                // 타이머 종료 이벤트
                override fun onFinish() {
                    binding.timerText.text="00:00"
                    Toast.makeText(this@MainActivity, "타이머 종료!", Toast.LENGTH_SHORT).show()
                    updateButtonState(start = true, stop=false, reset=false)
                    isPaused=false
                }
            }.start()   // 결론적으로 timer.start()가 된다.

            updateButtonState(start = false, stop = true, reset = true)
            isPaused=false
        }

        // 일시 정지 버튼
        binding.stopButton.setOnClickListener {
            timer?.cancel()
            isPaused = true
            updateButtonState(start = true, stop = false, reset = true)
        }

        // 초기화 버튼
        binding.resetButton.setOnClickListener {
            timer?.cancel()
            binding.timerText.text="00:00"
            timeLeft=0L
            isPaused=false
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

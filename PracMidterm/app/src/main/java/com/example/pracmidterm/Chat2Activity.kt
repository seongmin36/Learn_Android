package com.example.pracmidterm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pracmidterm.databinding.ActivityChat2Binding

class Chat2Activity: AppCompatActivity() {
    private lateinit var binding: ActivityChat2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChat2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 메시지 받기
        val receivedMsg = intent.getStringExtra("msgFrom1")
        if(receivedMsg != null) {
            binding.chat2.append("👤 Chat1: $receivedMsg\n")
        }

        // 메시지 보내기
        binding.btnSend.setOnClickListener {
            val sendMsg = binding.editMsg.text.toString().trim()
            if(sendMsg.isNotEmpty()) {
                // 내가 입력한 메시지 띄워 놓기
                binding.chat2.append("🧍‍♀️ 나: $sendMsg\n")

                // chat1Activity로 이동하여 메시지 확인
                val intent = Intent(this, Chat1Activity::class.java)
                intent.putExtra("msgFrom2", sendMsg)
                startActivity(intent)

                // 입력창 공백 처리
                binding.editMsg.text.clear()
            }
        }
    }
}
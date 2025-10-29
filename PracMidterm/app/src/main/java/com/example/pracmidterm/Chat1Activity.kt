package com.example.pracmidterm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pracmidterm.Chat2Activity
import com.example.pracmidterm.databinding.ActivityChat1Binding

class Chat1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityChat1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChat1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 Chat2에서 전달된 메시지 표시
        val receivedMsg = intent.getStringExtra("msgFrom2")
        if (receivedMsg != null) {
            binding.chat1.append("👤 Chat2: $receivedMsg\n")
        }

        // 🔹 보내기 버튼 클릭 시
        binding.btnSend.setOnClickListener {
            val msg = binding.editMsg.text.toString().trim()
            if (msg.isNotEmpty()) {
                // 내가 보낸 메시지 화면에 표시
                binding.chat1.append("🧍‍♂️ 나: $msg\n")

                // Chat2Activity로 이동하면서 메시지 전달
                val intent = Intent(this, Chat2Activity::class.java)
                intent.putExtra("msgFrom1", msg)
                startActivity(intent)

                // 입력창 비우기
                binding.editMsg.text.clear()
            }
        }
    }
}

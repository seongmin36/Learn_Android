package com.example.pracmidterm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pracmidterm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Animal.setOnClickListener {
        val intent = Intent(this, VoteActivity::class.java)
        startActivity(intent)
        }

        binding.chatroom.setOnClickListener {
            val intent = Intent(this, Chat1Activity::class.java)
            startActivity(intent)
        }

        binding.endBtn.setOnClickListener {
            finishAffinity()
        }
    }
}
package com.example.pracmidterm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pracmidterm.databinding.ActivityChat2Binding

class Chat2Activity: AppCompatActivity() {
    private lateinit var binding: ActivityChat2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChat2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
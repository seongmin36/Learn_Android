package com.example.pracmidterm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pracmidterm.databinding.ActivityChat1Binding

class Chat1Activity: AppCompatActivity() {
    private lateinit var binding: ActivityChat1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChat1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
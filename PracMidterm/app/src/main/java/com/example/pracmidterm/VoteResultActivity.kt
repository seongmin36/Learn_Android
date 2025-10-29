package com.example.pracmidterm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pracmidterm.databinding.ActivityVoteResultBinding

class VoteResultActivity: AppCompatActivity() {
    private lateinit var binding : ActivityVoteResultBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
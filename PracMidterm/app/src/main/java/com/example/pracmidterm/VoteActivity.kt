package com.example.pracmidterm

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pracmidterm.databinding.ActivityVoteBinding

class VoteActivity: AppCompatActivity() {
    private lateinit var binding : ActivityVoteBinding

    private var dogCnt = 0;
    private var catCnt = 0;
    private var rabbitCnt = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.voteBtn.setOnClickListener {
            onVote()
        }

        binding.resultBtn.setOnClickListener {
            val intent = Intent(this, VoteResultActivity::class.java)

            startActivity(intent)
        }
    }

    private fun onVote() {
        when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioDog -> {
                dogCnt++
            }
            R.id.radioCat -> {
                catCnt++
            }
            R.id.radioRabbit -> {
                rabbitCnt++
            }
            else -> {
                Toast.makeText(this, "먼저 동물을 선택하세요", Toast.LENGTH_SHORT)
                return
            }
        }
        val result = buildString {
            if(dogCnt > 0) append("Dog: ${dogCnt}표\n")
            if(catCnt > 0) append("Dog: ${catCnt}표\n")
            if(rabbitCnt > 0) append("Dog: ${rabbitCnt}표\n")
        }

        binding.voteText
    }
}
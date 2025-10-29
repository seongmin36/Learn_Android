package com.example.pracmidterm

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pracmidterm.databinding.ActivityVoteBinding
import com.example.pracmidterm.utils.VoteUtils

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
            intent.putExtra("dogCnt", dogCnt)
            intent.putExtra("catCnt", catCnt)
            intent.putExtra("rabbitCnt", rabbitCnt)

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
                Toast.makeText(this, "먼저 동물을 선택하세요", Toast.LENGTH_SHORT).show()
                return
            }
        }
        binding.voteView.text= VoteUtils.resultVote(dogCnt, catCnt, rabbitCnt)
    }
}
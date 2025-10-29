package com.example.pracmidterm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pracmidterm.databinding.ActivityVoteResultBinding
import com.example.pracmidterm.utils.VoteUtils

class VoteResultActivity: AppCompatActivity() {
    private lateinit var binding : ActivityVoteResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dogCnt = intent.getIntExtra("dogCnt", 0)
        val catCnt = intent.getIntExtra("catCnt", 0)
        val rabbitCnt = intent.getIntExtra("rabbitCnt", 0)

        val voteResult = VoteUtils.resultVote(dogCnt, catCnt, rabbitCnt)
        binding.voteResult.text = voteResult

        binding.voteBackBtn.setOnClickListener {
            finish()
        }
    }
}
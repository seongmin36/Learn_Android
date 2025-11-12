package com.example.my_recyclerview

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my_recyclerview.databinding.ActivitySubBinding

class SubActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "조성민 서울시 도봉구"

        val months = (1..12).map { it }

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = MonthAdapter(months) { month ->
            val (bg, season) = seasonColorAndText(month)
            binding.rootLayout.setBackgroundColor(bg)
            Toast.makeText(this, "${month}월은 ${season} 입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun seasonColorAndText(month: Int): Pair<Int, String> {
        return when (month) {
            12, 1, 2 -> (0x80FFFFFF).toInt() to "겨울"
            3, 4, 5 -> (0x80FFFF00).toInt() to "봄"
            6, 7, 8 -> (0x80FF0000).toInt() to "여름"
            else -> (0x800000FF).toInt() to "가을"
        }
    }
}

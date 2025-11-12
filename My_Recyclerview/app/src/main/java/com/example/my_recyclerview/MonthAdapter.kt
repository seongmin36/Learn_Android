package com.example.my_recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_recyclerview.databinding.ItemMonthBinding

class MonthAdapter(
    private val months: List<Int>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<MonthAdapter.VH>() {

    inner class VH(private val binding: ItemMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(month: Int) {
            binding.tvMonth.text = "${month}월"
            binding.root.setOnClickListener { onClick(month) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(months[position])
    }

    override fun getItemCount(): Int = months.size
}

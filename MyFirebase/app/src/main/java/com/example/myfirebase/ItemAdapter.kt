package com.example.myfirebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirebase.databinding.ItemRowBinding

class ItemAdapter(
    // 표시할 Item 리스트
    private val items: MutableList<Item>,
    // 삭제 버튼 클릭 시 실행할 함수
    private val onDeleteClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // ViewBinding을 사용하는 ViewHolder
    inner class ItemViewHolder(val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    // item_row.xml을 inflate하여 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    // 실제 데이터를 하나씩 화면에 표시
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.binding.prName.text = item.name
        holder.binding.prPriceQuantity.text = "${item.price}원 x ${item.quantity}"

        // 삭제 버튼 클릭 → MainActivity로 item 전달
        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    // Firebase에서 새 목록을 받아온 뒤 RecyclerView 갱신
    fun updateItems(newList: List<Item>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}

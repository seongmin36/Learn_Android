package com.example.assign09

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assign09.databinding.ItemRoomBinding
import com.example.assign09.model.Room

class ChatRoomListAdapter(private val rooms: List<Room>)
    : RecyclerView.Adapter<ChatRoomListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(rooms[position])
    }

    override fun getItemCount() = rooms.size

    inner class Holder(private val binding: ItemRoomBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(room: Room) {
            binding.textRoomTitle.text = room.title
            binding.textMsgCount.text = room.messageCount.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatRoomActivity::class.java)
                intent.putExtra("roomId", room.id)
                intent.putExtra("roomTitle", room.title)
                itemView.context.startActivity(intent)
            }
        }
    }
}

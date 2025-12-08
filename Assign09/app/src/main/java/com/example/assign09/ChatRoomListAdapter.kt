package com.example.assign09

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assign09.model.Room

class ChatRoomListAdapter(val roomList: MutableList<Room>)
    : RecyclerView.Adapter<ChatRoomListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(roomList[position])
    }

    override fun getItemCount(): Int = roomList.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textTitle = itemView.findViewById<TextView>(R.id.textRoomTitle)
        val textCount = itemView.findViewById<TextView>(R.id.textMsgCount)

        lateinit var mRoom: Room

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatRoomActivity::class.java)
                intent.putExtra("roomId", mRoom.id)
                intent.putExtra("roomTitle", mRoom.title)
                itemView.context.startActivity(intent)
            }
        }

        fun bind(room: Room) {
            mRoom = room
            textTitle.text = room.title
            textCount.text = room.messageCount.toString()
        }
    }
}

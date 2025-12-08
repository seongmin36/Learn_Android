package com.example.assign09

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assign09.databinding.ItemMsgListBinding
import com.example.assign09.model.Message

class MsgListAdapter(val msgList: MutableList<Message>)
    : RecyclerView.Adapter<MsgListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMsgListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setMsg(msgList[position])
    }

    override fun getItemCount(): Int = msgList.size

    class Holder(val binding: ItemMsgListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setMsg(msg: Message) {
            binding.textName.text = msg.userName
            binding.textMsg.text = msg.msg
            binding.textDate.text = msg.timestamp.toString()
        }
    }
}

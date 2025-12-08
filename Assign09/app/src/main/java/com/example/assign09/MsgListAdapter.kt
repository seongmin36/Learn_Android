package com.example.assign09

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assign09.databinding.ItemMsgListBinding
import com.example.assign09.model.Message

class MsgListAdapter(private val msgList: MutableList<Message>) :
    RecyclerView.Adapter<MsgListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMsgListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(msgList[position])
    }

    override fun getItemCount() = msgList.size

    class Holder(private val binding: ItemMsgListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: Message) {

            binding.textName.text = msg.userName
            binding.textDate.text = msg.timestamp.toString()

            // 기본적으로 모두 GONE
            binding.textMsg.visibility = View.GONE
            binding.imageMsg.visibility = View.GONE
            binding.textFileName.visibility = View.GONE

            when (msg.msgType) {

                "TEXT" -> {
                    binding.textMsg.visibility = View.VISIBLE
                    binding.textMsg.text = msg.msg
                }

                "IMAGE" -> {
                    binding.imageMsg.visibility = View.VISIBLE
                    Glide.with(binding.root)
                        .load(msg.fileUrl)
                        .into(binding.imageMsg)
                }

                "FILE" -> {
                    binding.textFileName.visibility = View.VISIBLE
                    binding.textFileName.text = "📎 ${msg.fileName}"

                    binding.textFileName.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(msg.fileUrl)
                        binding.root.context.startActivity(intent)
                    }
                }
            }
        }
    }
}

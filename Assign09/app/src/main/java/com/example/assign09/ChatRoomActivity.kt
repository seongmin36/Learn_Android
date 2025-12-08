package com.example.assign09

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.example.assign09.databinding.ActivityChatRoomBinding
import com.example.assign09.model.Message

class ChatRoomActivity : AppCompatActivity() {

    val binding by lazy { ActivityChatRoomBinding.inflate(layoutInflater) }
    val database = FirebaseDatabase.getInstance("https://fir-chat1204-default-rtdb.firebaseio.com/")
    lateinit var msgRef: DatabaseReference

    var roomId: String = ""
    var roomTitle: String = ""

    val msgList = mutableListOf<Message>()
    lateinit var adapter: MsgListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        roomId = intent.getStringExtra("roomId") ?: "none"
        roomTitle = intent.getStringExtra("roomTitle") ?: "없음"

        msgRef = database.getReference("rooms").child(roomId).child("messages")

        adapter = MsgListAdapter(msgList)
        binding.recyclerMsgs.adapter = adapter
        binding.recyclerMsgs.layoutManager = LinearLayoutManager(this)

        binding.textTitle.text = roomTitle
        binding.btnBack.setOnClickListener { finish() }
        binding.btnSend.setOnClickListener { sendMsg() }

        loadMsgs()
    }

    fun loadMsgs() {
        msgRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                msgList.clear()
                for (item in snapshot.children) {
                    item.getValue(Message::class.java)?.let { msg ->
                        msgList.add(msg)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun sendMsg() {
        val text = binding.editMsg.text.toString()
        if (text.isEmpty()) return

        val message = Message(text, ChatListActivity.userName)
        val msgId = msgRef.push().key!!
        message.id = msgId

        msgRef.child(msgId).setValue(message)

        val roomRef = database.getReference("rooms").child(roomId)
        roomRef.child("messageCount").get().addOnSuccessListener {
            val count = it.getValue(Long::class.java) ?: 0L
            roomRef.child("messageCount").setValue(count + 1)
        }

        binding.editMsg.setText("")
    }
}

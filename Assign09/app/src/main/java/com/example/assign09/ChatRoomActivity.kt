package com.example.assign09

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.assign09.databinding.ActivityChatRoomBinding
import com.example.assign09.model.Message
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class ChatRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatRoomBinding
    private val database = FirebaseDatabase.getInstance("https://fir-chat1204-default-rtdb.firebaseio.com/")

    private lateinit var msgRef: DatabaseReference
    private lateinit var roomRef: DatabaseReference

    private var roomId: String = ""
    private var roomTitle: String = ""

    private val msgList = mutableListOf<Message>()
    private lateinit var adapter: MsgListAdapter

    // 이미지 선택 런처
    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { uploadFileToStorage(it, "IMAGE") }
        }

    // 파일 선택 런처
    private val filePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { uploadFileToStorage(it, "FILE") }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        roomId = intent.getStringExtra("roomId") ?: "none"
        roomTitle = intent.getStringExtra("roomTitle") ?: "없음"

        msgRef = database.getReference("rooms").child(roomId).child("messages")
        roomRef = database.getReference("rooms").child(roomId)

        adapter = MsgListAdapter(msgList)
        binding.recyclerMsgs.adapter = adapter
        binding.recyclerMsgs.layoutManager = LinearLayoutManager(this)

        binding.textTitle.text = roomTitle
        binding.btnBack.setOnClickListener { finish() }

        // 기존 텍스트 메시지 전송
        binding.btnSend.setOnClickListener { sendTextMsg() }

        // 이미지 업로드 버튼
        binding.btnImage.setOnClickListener {
            imagePicker.launch("image/*")
        }

        // 파일 업로드 버튼
        binding.btnFile.setOnClickListener {
            filePicker.launch("*/*")
        }

        loadMsgs()
    }

    private fun loadMsgs() {
        msgRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                msgList.clear()
                for (item in snapshot.children) {
                    item.getValue(Message::class.java)?.let { msgList.add(it) }
                }
                adapter.notifyDataSetChanged()
                binding.recyclerMsgs.scrollToPosition(msgList.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    /** -------------------------------
     *  1) 텍스트 메시지 전송
     * -------------------------------- */
    private fun sendTextMsg() {
        val text = binding.editMsg.text.toString()
        if (text.isEmpty()) return

        val msgId = msgRef.push().key!!
        val msg = Message(
            msg = text,
            userName = ChatListActivity.userName,
            msgType = "TEXT"
        ).apply { id = msgId }

        msgRef.child(msgId).setValue(msg)

        // 메시지 카운트 증가
        roomRef.child("messageCount").get().addOnSuccessListener {
            val count = it.getValue(Long::class.java) ?: 0
            roomRef.child("messageCount").setValue(count + 1)
        }

        binding.editMsg.setText("")
    }

    /** -------------------------------
     *  2) 이미지/파일 업로드 처리
     * -------------------------------- */
    private fun uploadFileToStorage(uri: Uri, type: String) {

        val storageRef = FirebaseStorage.getInstance().reference
        val fileName = "${System.currentTimeMillis()}"
        val ref = storageRef.child("rooms/$roomId/$fileName")

        ref.putFile(uri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { url ->
                    saveMessageWithFile(url.toString(), fileName, type)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "업로드 실패", Toast.LENGTH_LONG).show()
            }
    }

    private fun saveMessageWithFile(url: String, fileName: String, type: String) {
        val msgId = msgRef.push().key!!

        val msg = Message(
            msg = "",
            userName = ChatListActivity.userName,
            msgType = type,
            fileUrl = url,
            fileName = fileName
        ).apply { id = msgId }

        msgRef.child(msgId).setValue(msg)

        roomRef.child("messageCount").get().addOnSuccessListener {
            val count = it.getValue(Long::class.java) ?: 0
            roomRef.child("messageCount").setValue(count + 1)
        }
    }
}

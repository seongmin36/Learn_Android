package com.example.assign09

import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assign09.databinding.ActivityChatListBinding
import com.example.assign09.model.Room
import com.google.firebase.database.*

class ChatListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatListBinding
    private val database = FirebaseDatabase.getInstance("https://fir-chat1204-default-rtdb.firebaseio.com/")
    private val roomsRef = database.getReference("rooms")

    companion object {
        var userId: String = ""
        var userName: String = ""
    }

    private val roomList = mutableListOf<Room>()
    private lateinit var adapter: ChatRoomListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId") ?: "none"
        userName = intent.getStringExtra("userName") ?: "Anonymous"

        adapter = ChatRoomListAdapter(roomList)
        binding.recyclerRooms.adapter = adapter
        binding.recyclerRooms.layoutManager = LinearLayoutManager(this)

        binding.btnCreate.setOnClickListener { openCreateRoomDialog() }

        loadRooms()
    }

    private fun loadRooms() {
        roomsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                roomList.clear()
                for (item in snapshot.children) {
                    item.getValue(Room::class.java)?.let { roomList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun openCreateRoomDialog() {
        val editText = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("방 이름")
            .setView(editText)
            .setPositiveButton("만들기") { _, _ ->
                val title = editText.text.toString()
                if (title.isEmpty()) {
                    Toast.makeText(this, "방 이름을 입력하세요", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                checkRoomDuplicate(title)
            }
            .show()
    }

    private fun checkRoomDuplicate(title: String) {
        roomsRef.orderByChild("title").equalTo(title)
            .get().addOnSuccessListener {
                if (it.exists()) {
                    Toast.makeText(this, "이미 존재하는 방 이름입니다.", Toast.LENGTH_SHORT).show()
                } else {
                    createRoom(title)
                }
            }
    }

    private fun createRoom(title: String) {
        val roomId = roomsRef.push().key!!
        val room = Room(title, userName).apply { id = roomId }
        roomsRef.child(roomId).setValue(room)
    }
}

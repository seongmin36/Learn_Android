package com.example.assign09

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.example.assign09.databinding.ActivityChatListBinding
import com.example.assign09.model.Room

class ChatListActivity : AppCompatActivity() {

    val binding by lazy { ActivityChatListBinding.inflate(layoutInflater) }
    val database = FirebaseDatabase.getInstance("https://fir-chat1204-default-rtdb.firebaseio.com/")
    val roomsRef = database.getReference("rooms")

    companion object {
        var userId: String = ""
        var userName: String = ""
    }

    val roomList = mutableListOf<Room>()
    lateinit var adapter: ChatRoomListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId") ?: "none"
        userName = intent.getStringExtra("userName") ?: "Anonymous"

        binding.btnCreate.setOnClickListener { openCreateRoom() }

        adapter = ChatRoomListAdapter(roomList)
        binding.recyclerRooms.adapter = adapter
        binding.recyclerRooms.layoutManager = LinearLayoutManager(this)

        loadRooms()
    }

    fun loadRooms() {
        roomsRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                roomList.clear()
                for (item in snapshot.children) {
                    item.getValue(Room::class.java)?.let { room ->
                        roomList.add(room)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun openCreateRoom() {
        val editTitle = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("방 이름")
            .setView(editTitle)
            .setPositiveButton("만들기") { dlg, id ->
                val title = editTitle.text.toString()
                if (title.isNotEmpty()) {
                    checkDuplicateAndCreate(title)
                }
            }
            .show()
    }

    fun checkDuplicateAndCreate(title: String) {
        roomsRef.orderByChild("title").equalTo(title).get().addOnSuccessListener {
            if (it.exists()) {
                Toast.makeText(this, "같은 이름의 방이 이미 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                createRoom(title)
            }
        }
    }

    fun createRoom(title: String) {
        val roomId = roomsRef.push().key!!
        val room = Room(title, userName)
        room.id = roomId

        roomsRef.child(roomId).setValue(room)
    }
}

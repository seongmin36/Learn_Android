package com.example.assign09

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.assign09.databinding.ActivityMainBinding
import com.example.assign09.model.User
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val database = FirebaseDatabase.getInstance("https://fir-chat1204-default-rtdb.firebaseio.com/")
    private val usersRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnSignin.setOnClickListener { signin() }
            btnSignup.setOnClickListener { signup() }
        }
    }

    private fun signup() = with(binding) {
        val id = editId.text.toString()
        val pw = editPassword.text.toString()
        val name = editName.text.toString()

        if (id.isEmpty() || pw.isEmpty() || name.isEmpty()) {
            Toast.makeText(this@MainActivity, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show()
            return@with
        }

        usersRef.child(id).get().addOnSuccessListener {
            if (it.exists()) {
                Toast.makeText(this@MainActivity, "아이디가 존재합니다.", Toast.LENGTH_SHORT).show()
            } else {
                usersRef.child(id).setValue(User(id, pw, name))
                signin()
            }
        }
    }

    private fun signin() = with(binding) {
        val id = editId.text.toString()
        val pw = editPassword.text.toString()

        if (id.isEmpty() || pw.isEmpty()) {
            Toast.makeText(this@MainActivity, "아이디/비밀번호 입력", Toast.LENGTH_SHORT).show()
            return@with
        }

        usersRef.child(id).get().addOnSuccessListener { snap ->
            if (!snap.exists()) {
                Toast.makeText(this@MainActivity, "아이디 없음", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            val user = snap.getValue(User::class.java)!!
            if (user.password == pw) {
                goChatList(user.id, user.name)
            } else {
                Toast.makeText(this@MainActivity, "비밀번호 오류", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goChatList(id: String, name: String) {
        val intent = Intent(this, ChatListActivity::class.java)
        intent.putExtra("userId", id)
        intent.putExtra("userName", name)
        startActivity(intent)
    }
}

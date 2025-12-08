package com.example.assign09

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.example.assign09.databinding.ActivityMainBinding
import com.example.assign09.model.User

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val database = FirebaseDatabase.getInstance("https://fir-chat1204-default-rtdb.firebaseio.com/")
    val usersRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignin.setOnClickListener { signin() }
        binding.btnSignup.setOnClickListener { signup() }
    }

    fun signup() {
        val id = binding.editId.text.toString()
        val password = binding.editPassword.text.toString()
        val name = binding.editName.text.toString()

        if (id.isEmpty() || password.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "아이디, 비밀번호, 별명을 모두 입력해야 합니다.", Toast.LENGTH_LONG).show()
            return
        }

        usersRef.child(id).get().addOnSuccessListener {
            if (it.exists()) {
                Toast.makeText(this, "아이디가 존재합니다.", Toast.LENGTH_LONG).show()
            } else {
                val user = User(id, password, name)
                usersRef.child(id).setValue(user)
                signin()
            }
        }
    }

    fun signin() {
        val id = binding.editId.text.toString()
        val password = binding.editPassword.text.toString()

        if (id.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "아이디와 비밀번호를 입력해야 합니다.", Toast.LENGTH_LONG).show()
            return
        }

        usersRef.child(id).get().addOnSuccessListener {
            if (it.exists()) {
                val user = it.getValue(User::class.java)
                if (user != null && user.password == password) {
                    goChatroomList(user.id, user.name)
                } else {
                    Toast.makeText(this, "비밀번호가 잘못되었습니다.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "아이디가 존재하지 않습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun goChatroomList(userId: String, userName: String) {
        val intent = Intent(this, ChatListActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("userName", userName)
        startActivity(intent)
    }
}

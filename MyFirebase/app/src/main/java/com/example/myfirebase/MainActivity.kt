package com.example.myfirebase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirebase.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Firebase Realtime Database의 "items" 노드를 가리킴
    private lateinit var dbRef: DatabaseReference

    // RecyclerView 어댑터
    private lateinit var adapter: ItemAdapter
    private val itemList = mutableListOf<Item>() // 표시할 Item 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Firebase 루트/items 노드
        dbRef = Firebase.database.reference.child("items")
        // RecyclerView 설정
        adapter = ItemAdapter(itemList) { item ->
            deleteItem(item)   // 삭제 버튼 클릭 시 실행
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // 저장 버튼
        binding.btnSave.setOnClickListener {
            saveItem()
        }

        // DB 변화를 감지해서 목록과 합계 업데이트
        listenForChanges()
    }

    // Firebase에 Item 저장
    private fun saveItem() {
        val name = binding.Name.text.toString()
        val price = binding.Price.text.toString().toIntOrNull() ?: 0
        val quantity = binding.Quantity.text.toString().toIntOrNull() ?: 0

        if (name.isEmpty()) {
            Log.e("Save", "상품명을 입력하세요")
            return
        }

        // push()로 고유 key 생성
        val newRef = dbRef.push()
        val item = Item(name, price, quantity, newRef.key ?: "")

        newRef.setValue(item)
            .addOnSuccessListener {

                // 입력칸 초기화
                binding.Name.text.clear()
                binding.Price.text.clear()
                binding.Quantity.text.clear()
            }
            .addOnFailureListener { e ->
                Log.e("Save", "저장 실패: ${e.message}")
            }
    }

    // DB 변경 감지 → 목록 + 합계 자동 갱신
    private fun listenForChanges() {
        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = mutableListOf<Item>()
                var total = 0

                for (child in snapshot.children) {
                    val item = child.getValue(Item::class.java)

                    if (item != null) {
                        temp.add(item)
                        total += item.price * item.quantity
                    }
                }

                adapter.updateItems(temp)  // 목록 갱신
                binding.Total.text = "총 합계: $total 원"  // 합계 출력
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "DB 오류: ${error.message}")
            }
        })
    }

    // Firebase에서 해당 항목 삭제
    private fun deleteItem(item: Item) {
        dbRef.child(item.key).removeValue()
    }
}

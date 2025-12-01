package com.example.myfirebase

// Typescript의 type 생성과 비슷
// Firebase에서 데이터를 받아올 때 반드시 기본 생성자 + 기본값 필요
data class Item(
    var name: String ="",
    var price: Int = 0,
    var quantity: Int = 0,
    var key: String = ""
)

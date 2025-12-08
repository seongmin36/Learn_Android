package com.example.assign09.model

class Room {
    var id: String = ""
    var title: String = ""
    var users: String = "" // 사용자 이름을 ,(쉼표) 로 구분해서 저장
    var messageCount: Long=0L

    constructor()

    constructor(title:String, creatorName: String) { // creator = 방 생성자
        this.title = title
        this.users = creatorName
        this.messageCount = 0L
    }
}
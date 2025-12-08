package com.example.assign09.model

class Message() {
    var id: String = ""
    var msg: String = ""          // 텍스트 메시지
    var userName: String = ""
    var timestamp: Long = 0

    var msgType: String = "TEXT"  // TEXT, IMAGE, FILE
    var fileUrl: String = ""      // 이미지 또는 파일 URL
    var fileName: String = ""     // 파일 이름 (파일 메시지일 때)

    constructor(
        msg: String,
        userName: String,
        msgType: String = "TEXT",
        fileUrl: String = "",
        fileName: String = ""
    ) : this() {
        this.msg = msg
        this.userName = userName
        this.timestamp = System.currentTimeMillis()
        this.msgType = msgType
        this.fileUrl = fileUrl
        this.fileName = fileName
    }
}

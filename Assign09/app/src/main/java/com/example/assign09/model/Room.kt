package com.example.assign09.model

class Room {
    var id: String = ""
    var title: String = ""
    var creator: String = ""
    var messageCount: Long = 0L

    constructor()

    constructor(title: String, creator: String) {
        this.title = title
        this.creator = creator
    }
}

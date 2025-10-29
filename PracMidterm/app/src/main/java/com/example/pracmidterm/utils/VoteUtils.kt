package com.example.pracmidterm.utils

object VoteUtils {
    fun resultVote(dogCnt: Int, catCnt: Int, rabbitCnt: Int):String {
        val result = buildString {
            if(dogCnt>0) append("Dog: ${dogCnt}표\n")
            if(catCnt>0) append("Cat: ${catCnt}표\n")
            if(rabbitCnt>0) append("Rabbit: ${rabbitCnt}표\n")
        }
        return if (result.isEmpty()) {
            "아직 투표 결과가 없습니다."
        } else {
            result.trim()
        }
    }
}
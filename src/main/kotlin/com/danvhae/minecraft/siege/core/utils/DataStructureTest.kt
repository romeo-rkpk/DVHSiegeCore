package com.danvhae.minecraft.siege.core.utils

import org.junit.Test
import kotlin.math.pow

class DataStructureTest {

    @Test
    fun stackTest(){
        val stack = DVHStack<Int>()
        for(i in 0 .. 10)
            stack.push(i)

        repeat(15){
            println("pick:${stack.pick()} pop : ${stack.pop()} size:${stack.count} pick:${stack.pick()}")
        }
    }

    @Test
    fun queueTest(){
        val queue = DVHQueue<Int>()
        for(i in 0 .. 10)
            queue.enqueue(i)

        repeat(15){
            println("pick:${queue.dequeue()}")
        }
    }

    @Test
    fun test(){
        listOf("사백사십억 천백이십", "오억사천만", "삼천삼백삼십억 오천사백이십삼만 이백오").forEach {
            val num = NumberUtil.hangulToNumber(it)
            println(num?.let { s -> "국문 : $it 국문->숫자$s 국문->숫자->국문${NumberUtil.numberToHangul(s)}" }?:"$it 을 파싱하지 못했습니다")
        }
    }

    @Test
    fun numTest(){
        println(10000.0.pow(2))
    }
}
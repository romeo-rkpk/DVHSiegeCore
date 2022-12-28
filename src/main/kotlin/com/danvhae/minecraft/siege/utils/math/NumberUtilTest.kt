package com.danvhae.minecraft.siege.utils.math

import org.junit.Test

class NumberUtilTest{

    @Test
    fun test1(){
        for(i in listOf(10000, 1000000, 2000000, 4_0000_0000, 9_9999_9999)){
            println("${NumberUtil.numberToHangul(i)}입니다")
        }
    }
}
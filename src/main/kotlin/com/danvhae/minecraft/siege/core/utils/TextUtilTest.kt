package com.danvhae.minecraft.siege.core.utils

import org.junit.Test

class TextUtilTest {
    @Test
    fun test(){

        listOf("ㄷ", "덴", "데네브","ㅇ", "아크룩스", "벡", "베", "베e").forEach {
            val args = arrayListOf("데네브", "아크룩스", "베가", "베e가")
            println(TextUtil.onlyStartsWith(args, it))
        }

    }
}
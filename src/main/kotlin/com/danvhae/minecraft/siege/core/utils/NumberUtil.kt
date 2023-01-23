package com.danvhae.minecraft.siege.core.utils

import kotlin.math.pow

/**
 * 숫자 유틸 함수 클래스
 * @author ManHyeondeok, Juliet, Romeo
 */
class NumberUtil {
    companion object{
        private val HAN1 = listOf("", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구")
        private val HAN2 = listOf("", "십", "백", "천")
        private val HAN3 = listOf("", "만", "억", "조", "경", "해")

        fun numberToHangul(num:Int, separate:Boolean = true):String{
            return numberToHangul(num.toLong(), separate)
        }

        /**
         * 숫자를 받아 한글로 적어주는 함수
         */
        fun numberToHangul(num:Long, separate:Boolean = true):String{
            if(num == 0L)return "영" ; else if(num < 0) return "마이너스 " + numberToHangul(- num, separate)
            val numStr = num.toString()
            val buffer = StringBuffer()
            var index = numStr.length
            var hasHan3 = false
            for(s in numStr){
                val nowInt = s.digitToInt(); val han2Pick = (index - 1) % 4;
                val han3Pick = minOf((index - 1) / 4, HAN3.lastIndex)

                if(nowInt > 0) buffer.append(HAN1[nowInt]).append(HAN2[han2Pick])
                if(nowInt > 0 && han2Pick > 0) hasHan3 = false
                if(!hasHan3 && han2Pick == 0){
                    buffer.append(HAN3[han3Pick]).append(if(separate) " " else "")
                    hasHan3 = true
                }
                index--
            }
            return buffer.toString().trim()
        }

        fun hangulToNumber(str:String):Long?{
            val stack = DVHStack<Long>()
            var lastHan3 = HAN3.size
            for(s in str){
                when(s.toString()){
                    " " -> continue

                    in HAN1 -> {
                        if(stack.pick() in 1L .. 9L) return null
                        stack.push(HAN1.indexOf(s.toString()).toLong())
                    }
                    in HAN2 ->{
                        if(stack.pick() !in 1 .. 9L)stack.push(1)
                        stack.push(stack.pop()!! * 10.0.pow(HAN2.indexOf(s.toString())).toInt())
                    }

                    in HAN3 ->{
                        //stack.push(10000.0.pow(HAN3.indexOf(s.toString())).toInt())
                        HAN3.indexOf(s.toString()).let {
                            if(lastHan3 <= it)return null
                            if(stack.pick() == null)stack.push(1)
                            //stack.push(10000.0.pow(it).toInt())
                            lastHan3 = it
                            var sum:Long = 0
                            while(stack.pick() in 1 .. 9999L && !stack.isEmpty()){
                                sum += stack.pop()!!
                            }
                            stack.push(sum * 10000.0.pow(it).toInt())
                        }
                    }
                }
            }

            var sum:Long = 0
            while(!stack.isEmpty()){
                sum += stack.pop()!!
            }
            return sum
        }
    }
}
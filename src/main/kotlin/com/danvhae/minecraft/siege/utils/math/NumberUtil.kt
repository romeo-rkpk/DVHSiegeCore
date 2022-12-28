package com.danvhae.minecraft.siege.utils.math

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
            if(num == 0)return "영" ; else if(num < 0) return "마이너스 " + numberToHangul(- num, separate)
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
    }
}
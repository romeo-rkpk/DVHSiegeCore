package com.danvhae.minecraft.siege.utils

class Hangul(val c:Char) {


    companion object{
        val FIRST_CODES :List<Char> = listOf('ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
        val SECOND_CODES : List<Char> = listOf('ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅞ', 'ㅝ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ')
        val LAST_CODES : List<Char?> = listOf(null, 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
        const val HANGUL_FIRST = '가'
        const val DIV = 588;
    }

    fun isHangul():Boolean{
        if(c in FIRST_CODES || c in SECOND_CODES || c in LAST_CODES) return true
        if(c < HANGUL_FIRST || c > '힣') return false
        return true
    }

    fun first():Char?{
        if(!isHangul())return null
        return FIRST_CODES[(c - HANGUL_FIRST) / DIV]
    }

    fun second():Char?{
        if(!isHangul()) return null
        return SECOND_CODES[((c - HANGUL_FIRST) % DIV) / 28]
    }

    fun last():Char?{
        if(!isHangul())return null
        return LAST_CODES[(c - HANGUL_FIRST) % 28]

    }
}
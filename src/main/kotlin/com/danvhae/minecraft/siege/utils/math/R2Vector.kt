package com.danvhae.minecraft.siege.utils.math

import kotlin.math.pow
import kotlin.math.sqrt

class R2Vector(val a:Double, val b:Double) {
    operator fun get(idx:Int):Double{
        return when(idx){
            0 -> a
            1 -> b
            else -> error("올바르지 않은 범위입니다")
        }
    }

    /**
     * 벡터의 내적
     */
    operator fun times(other: R2Vector):Double{
        var sum = 0.0;
        repeat(2){
            sum += this[it] * other[it]
        }
        return sum
    }

    operator fun div(other:Double): R2Vector {
        return R2Vector(a / other, b / other)
    }

    operator fun minus(other: R2Vector): R2Vector {
        return R2Vector(a - other[0], b - other[1])
    }

    fun length():Double{
        var sum = 0.0;
        repeat(2){
            sum += this[it].pow(2)
        }

        return sqrt(sum)
    }
}
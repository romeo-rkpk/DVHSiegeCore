package com.danvhae.minecraft.siege.core.utils.math

import kotlin.math.max
import kotlin.math.min

class Interval(val low:Double, val high:Double) {
    fun intersect(other: Interval): Interval?{
        if(low > high || other.low > other.high) return null

        if(high < other.low)return null
        if(other.high < low)return null

        return Interval(max(low, other.low), min(high, other.high))
    }



    operator fun contains(element:Double):Boolean{
        if(low > high)return false
        return element in low .. high
    }
}
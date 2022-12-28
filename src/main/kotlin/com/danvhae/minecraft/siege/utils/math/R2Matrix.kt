package com.danvhae.minecraft.siege.utils.math

class R2Matrix(val a11:Double, val a12:Double, val a21:Double, val a22:Double) {
    val det:Double
    init{
        det = a11 * a22 - a12 * a21
    }

    constructor(u: R2Vector, v: R2Vector):this(u[0], v[0], u[1], v[1])

    fun inverse(): R2Matrix?{
        if(det == 0.0)return null
        return R2Matrix(a22, -a12, -a21, a11) / det
    }

    operator fun times(other: R2Vector): R2Vector {
        return R2Vector(
            a11 * other.a + a12 * other.b, a21 * other.a + a22 * other.b
        )
    }

    operator fun div(other:Double): R2Matrix {
        return R2Matrix(a11 / other, a12 / other, a21 / other, a22 / other)
    }
}
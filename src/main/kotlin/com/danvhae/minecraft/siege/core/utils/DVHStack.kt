package com.danvhae.minecraft.siege.core.utils

class DVHStack<T> {

    private var top:Node? = null
    private var count = 0

    fun push(value:T){
        top = Node(value, top)
        count++
    }

    fun pop():T?{
        return top?.let {
            count--
            top = it.link
            return@let it.value
        }
    }

    fun pick():T?{
        return top?.value
    }

    private inner class Node(val value:T, var link:Node?)
}
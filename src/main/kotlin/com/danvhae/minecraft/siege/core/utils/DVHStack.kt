package com.danvhae.minecraft.siege.core.utils

class DVHStack<T> {

    private var top:Node? = null
    var count = 0
        private set

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

    fun isEmpty():Boolean{
        return count == 0
    }

    fun pick():T?{
        return top?.value
    }

    private inner class Node(val value:T, var link:Node?)
}
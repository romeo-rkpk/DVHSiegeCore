package com.danvhae.minecraft.siege.core.utils

class DVHQueue<T> {
    private var front:Node? = null
    private var tail:Node? = null

    var size = 0
        internal  set

    fun enqueue(value:T){
        val node = Node(value, null)
        tail?.link = node
        tail = node
        if(front == null){
            front = node
        }
        size ++
    }

    fun dequeue():T?{
        val value = front?.value
        front = front?.link
        if(value != null)size--
        return value
    }

    private inner class Node(val value:T, var link:Node?)
}
package com.danvhae.minecraft.siege.abstracts

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class KotlinBukkitEventAbstract : Event(){
    companion object{
        protected val HANDLERS_LIST = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS_LIST
        }
    }
    override fun getHandlers(): HandlerList {
        return HANDLERS_LIST
    }
}
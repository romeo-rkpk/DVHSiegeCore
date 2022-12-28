package com.danvhae.minecraft.siege.events

import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class EnterRegionEvent(val id:String, val world:World, val player:Player) : Event(){
    companion object{
        private val HANDLERS_LIST = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS_LIST
        }
    }
    override fun getHandlers(): HandlerList {
        return HANDLERS_LIST
    }
}
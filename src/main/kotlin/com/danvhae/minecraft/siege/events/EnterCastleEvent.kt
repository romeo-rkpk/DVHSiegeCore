package com.danvhae.minecraft.siege.events

import com.danvhae.minecraft.siege.objects.SiegeCastle
import com.danvhae.minecraft.siege.objects.SiegePlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class EnterCastleEvent(val castleID:String, val player: Player) : Event() {

    val siegePlayer:SiegePlayer? = SiegePlayer.DATA[player.uniqueId]
    val castle = SiegeCastle.DATA[castleID]!!

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
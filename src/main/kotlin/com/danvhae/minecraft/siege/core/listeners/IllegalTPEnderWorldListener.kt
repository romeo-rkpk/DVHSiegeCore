package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

class IllegalTPEnderWorldListener : Listener {
    @EventHandler
    fun onTeleportToEndWorld(event:PlayerTeleportEvent){
        if(DVHSiegeCore.masterConfig.sirius)return
        if(event.to.world.environment != World.Environment.THE_END) return
        event.isCancelled = true
        event.player.teleport(DVHSiegeCore.masterConfig.meetingRoom.toLocation()!!)
        /*
        event.player.teleport(DVHSiegeCore.masterConfig.meetingRoom.toLocation()!!)
        Bukkit.getScheduler().runTask(DVHSiegeCore.instance){
            event.player.health = 0.0
        }

         */
    }
}
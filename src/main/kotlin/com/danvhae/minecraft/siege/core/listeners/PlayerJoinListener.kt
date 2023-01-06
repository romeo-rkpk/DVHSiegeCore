package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.objects.SiegeOperator
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {
    @EventHandler
    fun onPlayerJoin(event:PlayerJoinEvent){
        val player = event.player
        if(player.uniqueId in SiegePlayer.DATA.keys)return
        if(player.uniqueId in SiegeOperator)return

        player.teleport(DVHSiegeCore.masterConfig.slaveStore.toLocation()!!)

    }
}
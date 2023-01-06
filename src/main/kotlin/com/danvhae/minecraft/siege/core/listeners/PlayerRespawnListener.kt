package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.objects.SiegeOperator
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class PlayerRespawnListener : Listener {

    @EventHandler
    fun onSlaveRespawn(event:PlayerRespawnEvent){
        val uuid = event.player.uniqueId
        if(uuid in SiegePlayer.DATA.keys) return
        if(uuid in SiegeOperator) return
        event.respawnLocation = DVHSiegeCore.masterConfig.slaveStore.toLocation()!!
    }
}
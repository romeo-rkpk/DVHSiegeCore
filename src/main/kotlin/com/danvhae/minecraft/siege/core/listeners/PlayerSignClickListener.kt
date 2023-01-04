package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.events.DVHSignClickEvent
import org.bukkit.Bukkit
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerSignClickListener : Listener {
    @EventHandler
    fun onPlayerClickSign(event:PlayerInteractEvent){
        if(event.action !in listOf(Action.LEFT_CLICK_BLOCK, Action.RIGHT_CLICK_BLOCK))return
        (event.clickedBlock?.state as? Sign)?.let {
                sign->Bukkit.getPluginManager().callEvent(DVHSignClickEvent(sign, event))
        }
    }
}
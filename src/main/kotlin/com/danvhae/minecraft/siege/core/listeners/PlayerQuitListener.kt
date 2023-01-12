package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.commands.TimerCommand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener : Listener {

    @EventHandler
    fun onPlayerQuit(event:PlayerQuitEvent){
        TimerCommand.removePlayer(event.player)
    }
}
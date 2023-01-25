package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.commands.TimerCommand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {
    @EventHandler
    fun onPlayerJoinWithBar(event: PlayerJoinEvent){
        TimerCommand.addPlayer(event.player)
    }
}
package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.utils.NameUtil
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerNameCacheUpdateListener :  Listener{

    @EventHandler
    fun onPlayerJoin(event:PlayerJoinEvent){
        NameUtil.putCache(event.player.uniqueId, event.player.name)
    }

    @EventHandler
    fun onPlayerQuit(event:PlayerQuitEvent){
        NameUtil.removeCache(event.player.uniqueId)
    }
}
package com.danvhae.minecraft.siege.listeners

import com.danvhae.minecraft.siege.events.EnterCastleEvent
import com.danvhae.minecraft.siege.events.EnterRegionEvent
import com.danvhae.minecraft.siege.objects.SiegeCastle
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerEnterRegionListener : Listener {
    @EventHandler
    fun onEnterRegion(event:EnterRegionEvent){
        for(castle in SiegeCastle.DATA.values){
            if(event.id != castle.worldGuardID)continue
            Bukkit.getPluginManager().callEvent(EnterCastleEvent(castle.id, event.player))
        }
    }
}
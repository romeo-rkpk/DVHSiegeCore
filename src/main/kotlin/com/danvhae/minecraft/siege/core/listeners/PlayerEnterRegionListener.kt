package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.events.DistressStartEvent
import com.danvhae.minecraft.siege.core.events.EnterCastleEvent
import com.danvhae.minecraft.siege.core.events.EnterRegionEvent
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerEnterRegionListener : Listener {
    @EventHandler
    fun onEnterRegion(event: EnterRegionEvent){
        for(castle in SiegeCastle.DATA.values){
            if(event.id != castle.worldGuardID)continue
            Bukkit.getPluginManager().callEvent(EnterCastleEvent(castle.id, event.player))
        }
    }

    @EventHandler
    fun onEnterDistressRegion(event: EnterRegionEvent){
        if(event.id != DVHSiegeCore.DISTRESS_ZONE_ID)return
        Bukkit.getPluginManager().callEvent(DistressStartEvent(event.player))
    }
}
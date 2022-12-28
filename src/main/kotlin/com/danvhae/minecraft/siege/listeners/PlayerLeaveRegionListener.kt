package com.danvhae.minecraft.siege.listeners

import com.danvhae.minecraft.siege.events.LeaveRegionEvent
import com.danvhae.minecraft.siege.objects.SiegeCastle
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerLeaveRegionListener : Listener {

    @EventHandler
    fun onLeaveRegion(event: LeaveRegionEvent){
        for(castle in SiegeCastle.DATA.values){
            if(castle.worldGuardID != event.id)continue
            Bukkit.getPluginManager().callEvent(LeaveRegionEvent(castle.id, event.player))
        }
    }
}
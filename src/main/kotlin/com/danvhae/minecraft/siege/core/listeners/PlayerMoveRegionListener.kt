package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.events.EnterRegionEvent
import com.danvhae.minecraft.siege.core.events.LeaveRegionEvent
import com.danvhae.minecraft.siege.core.utils.LocationUtil
import com.danvhae.minecraft.siege.core.utils.PlayerUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.*

class PlayerMoveRegionListener : Listener {

    @EventHandler
    fun onPlayerMove(event:PlayerMoveEvent){
        compareAndRaiseEvent(LocationUtil.worldGuardIds(event.from), LocationUtil.worldGuardIds(event.to), event.player)
    }

    @EventHandler
    fun onPlayerDeath(event:PlayerDeathEvent){
        compareAndRaiseEvent(LocationUtil.worldGuardIds(event.entity.location), HashSet(), event.entity.player)
    }

    @EventHandler
    fun onRespawn(event:PlayerRespawnEvent){
        compareAndRaiseEvent(HashSet(), LocationUtil.worldGuardIds(event.respawnLocation), event.player)
    }

    @EventHandler
    fun onPlayerTeleport(event:PlayerTeleportEvent){
        compareAndRaiseEvent(LocationUtil.worldGuardIds(event.from), LocationUtil.worldGuardIds(event.to), event.player)
    }

    @EventHandler
    fun onJoin(event:PlayerJoinEvent){
        val temp = PlayerUtil.playerRegion(event.player)
        if(PlayerUtil.isDistressZone(event.player.location))
            temp.add(DVHSiegeCore.DISTRESS_ZONE_ID)
        compareAndRaiseEvent(HashSet(), temp, event.player)
    }

    @EventHandler
    fun onQuit(event:PlayerQuitEvent){
        val temp = PlayerUtil.playerRegion(event.player)
        if(PlayerUtil.isDistressZone(event.player.location))temp.add(DVHSiegeCore.DISTRESS_ZONE_ID)
        compareAndRaiseEvent(temp, HashSet(), event.player)
    }

    private fun compareAndRaiseEvent(before:HashSet<String>, after:HashSet<String>, player: Player){
        val afterMinusBefore = HashSet(after)

        afterMinusBefore.removeAll(before)
        for(s in afterMinusBefore){
            Bukkit.getPluginManager().callEvent(EnterRegionEvent(s, player))
        }

        val beforeMinusAfter = HashSet(before)
        beforeMinusAfter.removeAll(after)
        for(r in beforeMinusAfter)
            Bukkit.getPluginManager().callEvent(LeaveRegionEvent(r, player))
    }

}
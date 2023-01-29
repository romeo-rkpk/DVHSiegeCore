package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.events.CastleEliminatedEvent
import com.danvhae.minecraft.siege.core.events.LastCastleEliminateEvent
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.objects.SiegeTeam
import com.danvhae.minecraft.siege.core.utils.LocationUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class CastleEliminatedListener : Listener {

    @EventHandler
    fun onEliminated(event:CastleEliminatedEvent){

        event.castle.status = SiegeCastleStatus.ELIMINATED
        for(player in Bukkit.getOnlinePlayers()){
            SiegePlayer.DATA[player.uniqueId]?:continue
            if(event.castle !in LocationUtil.locationAtStars(player.location))continue
            player.teleport(DVHSiegeCore.masterConfig.meetingRoom.toLocation()!!)
        }

        if(SiegeTeam.DATA[event.castle.team]!!.aliveCastle().isEmpty()){
            Bukkit.getServer().broadcastMessage("${event.killer.team}에 의하여 ${event.castle.team}... 탈락하였습니다")
            Bukkit.getPluginManager()
                .callEvent(LastCastleEliminateEvent(event.killer.getTeam()!!, SiegeTeam.DATA[event.castle.team]!!))

            for(player in Bukkit.getOnlinePlayers()){
                player.sendTitle(TextUtil.toColor("&4&l탈락 &f" +
                        ": ${SiegeTeam.DATA[event.castle.team]!!.colorPrefix}&l${event.castle.team}"),
                    "${event.castle.name} 멸망", 10, 100, 5)
            }
        }else{
            for(player in Bukkit.getOnlinePlayers()){
                player.sendTitle(TextUtil.toColor("&c멸망"), TextUtil.toColor("&f${event.castle.name}"),
                    10, 70, 5)
            }
        }


    }
}
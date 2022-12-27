package com.danvhae.minecraft.siege.objects

import com.danvhae.minecraft.siege.enums.SiegeCastleStatus
import java.util.*

class SiegeTeam(val name:String, val leader: SiegePlayer){

    private val players = HashMap<UUID, SiegePlayer>()
    fun players():List<SiegePlayer>{
        val list = ArrayList<SiegePlayer>()
        val keys = players.keys
        for(key in keys)
            list.add(players[key]?:continue)
        return list.toList()
    }

    fun addPlayer(player: SiegePlayer){
        players[player.playerUUID] = player
    }
    fun removePlayer(player: SiegePlayer){
        players.remove(player.playerUUID)
    }

    fun castles():List<SiegeCastle>{
        TODO("WIP")
    }
    fun aliveCastle():List<SiegeCastle>{
        val temp = ArrayList<SiegeCastle>()
        val alls = castles()
        for(castle in alls){
            if(castle.status in listOf(SiegeCastleStatus.PEACEFUL,
                    SiegeCastleStatus.UNDER_BATTLE))
                temp.add(castle)
        }
        return temp.toList()
    }
}
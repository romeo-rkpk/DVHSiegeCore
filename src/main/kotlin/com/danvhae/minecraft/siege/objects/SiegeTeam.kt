package com.danvhae.minecraft.siege.objects

import com.danvhae.minecraft.siege.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.utils.FileUtil
import com.google.gson.GsonBuilder
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

    class DAO(val team:String, val leader:String){

        constructor(team:SiegeTeam):this(team.name, team.leader.playerUUID.toString())

        companion object{
            private const val FILE_NAME = "teams.json"
        }

        fun save(teams:Array<SiegeTeam>){
            val list = ArrayList<DAO>()
            for(team in teams)
                list.add(DAO(team))
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = gson.toJson(list.toTypedArray())
            FileUtil.writeTextFile(json, FILE_NAME)
        }

        fun load():Array<SiegeTeam>{
            TODO("WIP")
        }
    }
}
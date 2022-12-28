package com.danvhae.minecraft.siege.objects

import com.danvhae.minecraft.siege.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*

class SiegeTeam(val name:String, val leaderUUID:UUID, var remark:String = ""){


    companion object{
        private val DATA = HashMap<String, SiegeTeam>()
        fun save(){
            DAO.save(DATA.values.toTypedArray())
        }

        fun load(){
            DATA.clear()
            val array = DAO.load()
            for(team in array){
                DATA[team.name] = team
            }
        }
    }

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
        val castleIDs = SiegeCastle.DATA.keys
        val arrList = ArrayList<SiegeCastle>()
        for(castleID in castleIDs){
            val castle = SiegeCastle.DATA[castleID]?:continue
            if(castle.owner == leaderUUID)arrList.add(castle)
        }

        return arrList.toList()
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

    private class DAO(val name:String, val leaderUUID:String, val remark: String){

        constructor(team:SiegeTeam):this(team.name, team.leaderUUID.toString(), team.remark)

        companion object{
            private const val FILE_NAME = "teams.json"
            fun save(teams:Array<SiegeTeam>){
                val list = ArrayList<DAO>()
                for(team in teams)
                    list.add(DAO(team))
                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.toJson(list.toTypedArray())
                FileUtil.writeTextFile(json, FILE_NAME)
            }

            fun load():Array<SiegeTeam>{
                val gson = Gson()
                val json = FileUtil.readTextFile(FILE_NAME)
                val arr = gson.fromJson(json, Array<DAO>::class.java)

                val result = ArrayList<SiegeTeam>()
                for(e in arr)
                    result.add(SiegeTeam(e.name, UUID.fromString(e.leaderUUID), e.remark))

                return result.toTypedArray()
            }
        }
    }
}
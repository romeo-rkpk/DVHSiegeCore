package com.danvhae.minecraft.siege.objects

import com.danvhae.minecraft.siege.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.UUID

class SiegePlayer(val playerUUID:UUID, var team: SiegeTeam, var isOwner:Boolean, val alias:String?) {

    class DAO(val playerUUID:String, val team: String, val isOwner: Boolean, val alias: String?){
        constructor(siegePlayer: SiegePlayer):this(siegePlayer.playerUUID.toString(), siegePlayer.team.name,
            siegePlayer.isOwner, siegePlayer.alias)

        companion object{
            private const val FILE_NAME = "players.json"
            fun save(players:Array<SiegePlayer>){
                val list = ArrayList<DAO>()
                for(p in players)
                    list.add(DAO(p))
                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.toJson(list.toTypedArray())
                FileUtil.writeTextFile(json, FILE_NAME)
            }

            fun load():Array<SiegePlayer>{
                val gson = Gson()
                val json = FileUtil.readTextFile(FILE_NAME)
                val arr = gson.fromJson(json, Array<DAO>::class.java)
                val list = ArrayList<SiegePlayer>()
                //for(e in arr)list.add(SiegePlayer(UUID.fromString(e.playerUUID)))
                TODO("WIP")
            }
        }
    }
}
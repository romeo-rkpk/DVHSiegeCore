package com.danvhae.minecraft.siege.objects

import com.danvhae.minecraft.siege.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.UUID

class SiegePlayer(val playerUUID:UUID, var team: String, var isOwner:Boolean, val alias:String?) {

    companion object{
        val DATA = HashMap<UUID, SiegePlayer>()

        fun load(){
            DATA.clear()
            val players = DAO.load()
            for(p in players)
                DATA[p.playerUUID] = p
        }

        fun save(){
            DAO.save(DATA.values.toTypedArray())
        }
    }

    private class DAO(val playerUUID:String, val team: String, val isOwner: Boolean, val alias: String?){
        constructor(siegePlayer: SiegePlayer):this(siegePlayer.playerUUID.toString(), siegePlayer.team,
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
                for(e in arr)
                    list.add(SiegePlayer(UUID.fromString(e.playerUUID), e.team, e.isOwner, e.alias))
                return list.toTypedArray()
            }
        }
    }
}
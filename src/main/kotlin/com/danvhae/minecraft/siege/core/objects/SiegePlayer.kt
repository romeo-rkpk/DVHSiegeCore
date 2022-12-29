package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.danvhae.minecraft.siege.core.utils.NameUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import java.util.*

class SiegePlayer(val playerUUID:UUID, team: String, isOwner:Boolean, val alias:String?) {

    var team:String = team
        internal set(value){
            val temp = field
            field = value
            //SiegeTeam.load()
            val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
            Bukkit.getScheduler().runTask(DVHSiegeCore.instance) {
                NameUtil.uuidToName(playerUUID)?.let {name ->
                    run {
                        scoreboard.getTeam(temp).removeEntry(name)
                        scoreboard.getTeam(field).addEntry(name)
                    }
                }
            }
            save()
            //scoreTeam.removeEntry()
        }

    init{
        this.team = this.team
    }

    var isOwner:Boolean = isOwner
        internal set

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

    /**
     * 이 플레이어의 팀 객체를 바로 가져옵니다.
     */
    fun getTeam(): SiegeTeam?{
        return SiegeTeam.DATA[team]
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
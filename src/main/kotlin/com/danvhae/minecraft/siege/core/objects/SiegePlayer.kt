package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.enums.SiegeGroup
import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.danvhae.minecraft.siege.core.utils.NameUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import java.util.*

class SiegePlayer(val playerUUID:UUID, team: String, isOwner:Boolean, alias:String?) {

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
            //scoreTeam.removeEntry()
        }

    var alias:String? = alias
        internal set

    init{
        this.team = this.team
    }

    var isOwner:Boolean = isOwner
        internal set

    companion object{
        val DATA = HashMap<UUID, SiegePlayer>()

        operator fun contains(uuid:UUID):Boolean{
            for(sPlayer in DATA.values){
                if(sPlayer.playerUUID == uuid)return true
            }
            return false
        }

        operator fun get(uuid:UUID): SiegePlayer?{
            for(sPlayer in DATA.values){
                if(sPlayer.playerUUID == uuid) return sPlayer
            }
            return null
        }

        fun load(){
            val cache = HashMap<UUID, SiegeGroup>()
            for(data in DATA.values){
                cache[data.playerUUID] = SiegeGroup.DEFAULT
            }
            val uuids = HashSet<UUID>()
            Bukkit.getWhitelistedPlayers().clear()

            DATA.clear()
            SiegeOperator.load()
            for(operator in SiegeOperator.DATA.values){
                cache[operator.uuid] = SiegeGroup.OPERATOR
                uuids.add(operator.uuid)
            }

            val players = DAO.load()
            for(p in players) {
                DATA[p.playerUUID] = p
                cache[p.playerUUID] = if(p.isOwner) SiegeGroup.OWNER else SiegeGroup.WORKER
                uuids.add(p.playerUUID)
            }

            for(c in cache){
                c.value.setGroup(c.key)
            }

            uuids.forEach{uuid ->
                Bukkit.getScheduler().runTask(DVHSiegeCore.instance){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add ${NameUtil.uuidToName(uuid)}")
                }
            }

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
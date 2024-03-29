package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.events.CastleDataChangedEvent
import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.*

class SiegeCastle(val id:String, val name:String, status: SiegeCastleStatus, team:String?,
                  attackPosition:Location, workPosition:Location,
                  worldGuardID:String, level:Int = 0){

    var status: SiegeCastleStatus = status
        set(value) {
            val temp = field
            field = value
            Bukkit.getPluginManager().callEvent(CastleDataChangedEvent(id, temp, field, team, team, level, level))
            save()
        }
    var team: String? = team
        set(value) {
            val temp = field
            field = value
            Bukkit.getPluginManager().callEvent(CastleDataChangedEvent(id, status, status, temp, field, level, level))
            save()
        }

    internal var level: Int = level
        set(value){
            val temp = field
            field = value
            Bukkit.getPluginManager().callEvent(CastleDataChangedEvent(id, status, status, team, team, temp, field))
            save()
        }
    var attackPosition:Location = attackPosition
        internal set

    var workPosition:Location = workPosition
        internal set

    var worldGuardID:String = worldGuardID
        internal set

    //저거 둘은 나중에 이벤트 호출 할 예정

    fun ownerPlayer(): SiegePlayer? {
        return SiegeTeam.DATA[team]?.leaderUUID?.let { SiegePlayer.DATA[it] }
    }

    companion object{
        val DATA = HashMap<String, SiegeCastle>()

        operator fun contains(id:String):Boolean{
            for(castle in DATA.values){
                if(id == castle.id)return true
            }
            return false
        }

        operator fun get(id:String):SiegeCastle?{
            for(castle in DATA.values){
                if(castle.id == id)return castle
            }
            return null
        }

        fun save(){
            DAO.save(DATA.values.toTypedArray())
        }

        fun load(){
            DATA.clear()
            val castles = DAO.load()
            for(castle in castles)
                DATA[castle.id] = castle
        }
    }

    private class DAO(val id:String, val name:String, val status: SiegeCastleStatus, val team:String?,
                      val attackPosition: LocationData, val workPosition: LocationData, val worldGuardID: String, val level:Int = 0){

        constructor(castle: SiegeCastle):this(castle.id, castle.name, castle.status,
            castle.team ,
            LocationData(castle.attackPosition),
            LocationData(castle.workPosition), castle.worldGuardID, castle.level
        )

        companion object{
            private const val FILE_NAME = "siegeCastle.json"
            fun save(castles:Array<SiegeCastle>){
                val list = ArrayList<DAO>()
                for(castle in castles)
                    list.add(DAO(castle))

                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.toJson(list.toTypedArray())
                FileUtil.writeTextFile(json, FILE_NAME)
            }

            fun load():Array<SiegeCastle>{
                val json = FileUtil.readTextFile(FILE_NAME)
                val gson = Gson()
                val arr = gson.fromJson(json, Array<DAO>::class.java)
                val list = ArrayList<SiegeCastle>()
                for(dao in arr){
                    Bukkit.getLogger().info("${dao.id} 데이터 로드 중")
                    //list.add(SiegeCastle(dao.id, dao.name, dao.status,))
                    //Bukkit.getLogger().info("${dao.attackPosition}")
                    list.add(SiegeCastle(dao.id, dao.name, dao.status,
                        dao.team, dao.attackPosition.toLocation()?:continue,
                        dao.workPosition.toLocation()?:continue, dao.worldGuardID, dao.level))
                    Bukkit.getLogger().info("${dao.name} 데이터 로드 완료")
                }

                return list.toTypedArray()
            }
        }


    }

}
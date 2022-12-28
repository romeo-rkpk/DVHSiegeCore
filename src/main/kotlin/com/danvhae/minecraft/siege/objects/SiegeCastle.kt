package com.danvhae.minecraft.siege.objects

import com.danvhae.minecraft.siege.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.*
import kotlin.collections.ArrayList

class SiegeCastle(val id:String, val name:String, status:SiegeCastleStatus, owner: UUID?,
                  var attackPosition:Location, var workPosition:Location, var worldGuardID:String){

    var status:SiegeCastleStatus = status
        set(value) {
            field = value

            save()
        }
    var owner: UUID? = owner
        set(value) {
            field = value

            save()
        }

    //저거 둘은 나중에 이벤트 호출 할 예정

    companion object{
        val DATA = HashMap<String, SiegeCastle>()

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

    private class DAO(val id:String, val name:String, val status:SiegeCastleStatus, val owner:String?,
                      val attackPosition: LocationData, val workPosition: LocationData, val worldGuardID: String){

        constructor(castle:SiegeCastle):this(castle.id, castle.name, castle.status, castle.owner?.toString(),
            LocationData(castle.attackPosition),
            LocationData(castle.workPosition), castle.worldGuardID
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
                        (dao.owner).let { if(it != null)UUID.fromString(it)else null }
                        , dao.attackPosition.toLocation()?:continue, dao.workPosition.toLocation()?:continue, dao.worldGuardID))
                    Bukkit.getLogger().info("${dao.name} 데이터 로드 완료")
                }

                return list.toTypedArray()
            }
        }


    }

}
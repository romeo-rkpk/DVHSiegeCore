package com.danvhae.minecraft.siege.objects

import com.danvhae.minecraft.siege.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Location

class SiegeCastle(val id:String, val name:String, var status:SiegeCastleStatus, var owner: SiegePlayer?,
                  var attackPosition:Location, var workPosition:Location, var worldGuardID:String){

    private class DAO(val id:String, val name:String, val status:SiegeCastleStatus, val owner:String?,
                      val attackPosition: LocationData, val workPosition: LocationData, val worldGuardID: String){

        constructor(castle:SiegeCastle):this(castle.id, castle.name, castle.status, castle.owner?.playerUUID.toString(),
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
                    //list.add(SiegeCastle(dao.id, dao.name, dao.status,))
                }

                TODO("WIP")
            }
        }


    }

}
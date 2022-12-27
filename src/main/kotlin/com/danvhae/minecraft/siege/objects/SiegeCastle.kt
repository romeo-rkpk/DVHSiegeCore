package com.danvhae.minecraft.siege.objects

import com.danvhae.minecraft.siege.abstracts.SiegePlayer
import com.danvhae.minecraft.siege.enums.SiegeCastleStatus
import org.bukkit.Location

class SiegeCastle(val id:String, val name:String, var status:SiegeCastleStatus, var owner:SiegePlayer?,
                  var attackPosition:Location, var workPosition:Location, var worldGuardID:String){

    private class DAO(val id:String, val name:String, val status:SiegeCastleStatus, val owner:String?,
                      val attackPosition: LocationData, val workPosition: LocationData, val worldGuardID: String){

        constructor(castle:SiegeCastle):this(castle.id, castle.name, castle.status, castle.owner?.playerUUID.toString(), LocationData(castle.attackPosition),
            LocationData(castle.workPosition), castle.worldGuardID
        )

        companion object{
            fun save(castles:Array<SiegeCastle>){

            }

            fun load():Array<SiegeCastle>{
                TODO("WIP")
            }
        }


    }

}
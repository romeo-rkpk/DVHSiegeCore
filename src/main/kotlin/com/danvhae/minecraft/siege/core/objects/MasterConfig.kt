package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.events.SiegeEndEvent
import com.danvhae.minecraft.siege.core.events.SiegeStartEvent
import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit

class MasterConfig(period:Boolean = false, var sirius:Boolean = false, var distressNotify: Boolean = false, var wildWorldName:String = "world",
                   var meetingRoom:LocationData = LocationData("spawn", 135.5, 105.5, 221.5),
                   var slaveStore:LocationData = LocationData("spawn", 1099.97, 72.0, 14.24)) {

    var period:Boolean = period
        set(value) {
            field = value
            Bukkit.getPluginManager().callEvent(if(value){
                    SiegeStartEvent()
                }else{
                    SiegeEndEvent()
                }
            )
        }
    companion object{
        private const val FILE_NAME = "master.json"

        fun load():MasterConfig{
            return try{
                val gson = Gson()
                val json = FileUtil.readTextFile(FILE_NAME)
                gson.fromJson(json, MasterConfig::class.java)
            }catch (_:Exception){
                MasterConfig()
            }
        }

        fun save(config:MasterConfig){
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = gson.toJson(config)
            FileUtil.writeTextFile(json, FILE_NAME)
        }
    }
}
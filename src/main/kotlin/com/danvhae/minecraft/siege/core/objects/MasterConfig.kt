package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MasterConfig(var period:Boolean = false, var sirius:Boolean = false, var wildWorldName:String = "world") {
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
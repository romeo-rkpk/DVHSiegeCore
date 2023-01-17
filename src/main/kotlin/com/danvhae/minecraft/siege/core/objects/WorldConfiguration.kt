package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.World

class WorldConfiguration(val name:String, castleWorld:Boolean) {

    var castleWorld:Boolean = castleWorld
        internal set
    companion object{

        operator fun get(world:World):WorldConfiguration?{
            return DATA[world.name]
        }

        operator fun contains(world: World):Boolean{
            return world.name in DATA.keys
        }

        private const val FILE_NAME = "worlds.json"

        private val DATA = HashMap<String, WorldConfiguration>()

        fun load(){
            DATA.clear()
            Gson().fromJson(FileUtil.readTextFile(FILE_NAME)?:"[]", Array<WorldConfiguration>::class.java).forEach {
                DATA[it.name] = it
            }
        }

        fun save(){
            FileUtil.writeTextFile(GsonBuilder().setPrettyPrinting().create().toJson(DATA.values.toTypedArray()), FILE_NAME)
        }
    }
}
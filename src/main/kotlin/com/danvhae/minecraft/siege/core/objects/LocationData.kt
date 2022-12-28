package com.danvhae.minecraft.siege.core.objects

import org.bukkit.Bukkit
import org.bukkit.Location

class LocationData(val worldName:String, val x:Double, val y:Double, val z:Double) {
    constructor(location:Location):this(location.world.name, location.x, location.y, location.z)
    fun toLocation():Location?{
        return Location(Bukkit.getWorld(worldName)?:return null, x, y, z)
    }
}
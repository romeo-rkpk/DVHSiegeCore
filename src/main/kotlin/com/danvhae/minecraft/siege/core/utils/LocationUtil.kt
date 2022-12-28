package com.danvhae.minecraft.siege.core.utils

import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import org.bukkit.Location

class LocationUtil {
    companion object{
        fun worldGuardIds(location: Location):HashSet<String>{
            val set = WorldGuardPlugin.inst().getRegionManager(location.world).getApplicableRegions(location)
            val result = HashSet<String>()
            for(r in set.regions?:return result)
                result.add(r.id)
            return result
        }
    }
}
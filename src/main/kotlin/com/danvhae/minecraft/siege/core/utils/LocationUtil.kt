package com.danvhae.minecraft.siege.core.utils

import com.danvhae.minecraft.siege.core.objects.SiegeCastle
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

        fun locationAtStars(location:Location):HashSet<SiegeCastle>{
            val result = HashSet<SiegeCastle>()
            val set = WorldGuardPlugin.inst().getRegionManager(location.world).getApplicableRegions(location)
            val regions = HashSet<String>()
            set.regions?.forEach { r -> regions.add(r.id) }?:return result
            for(castle in SiegeCastle.DATA.values){
                if(castle.attackPosition.world != location.world)continue
                if(castle.worldGuardID in regions)
                    result.add(castle)
            }
            return result
        }
    }
}
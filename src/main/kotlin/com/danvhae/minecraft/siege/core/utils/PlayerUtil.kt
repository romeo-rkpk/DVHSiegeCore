package com.danvhae.minecraft.siege.core.utils

import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import org.bukkit.Location
import org.bukkit.entity.Player

class PlayerUtil {
    companion object{
        fun playerRegion(player:Player):HashSet<String>{
            val result = HashSet<String>()

            val manager = WorldGuardPlugin.inst().getRegionManager(player.world)
            val set = manager.getApplicableRegions(player.location)?:return result
            if(player.health <= 0)return result
            for(region in set.regions?:return result)
                result.add(region.id)
            if(isDistressZone(player.location))result.add(DVHSiegeCore.DISTRESS_ZONE_ID)
            return result
        }

        fun isDistressZone(location: Location):Boolean{
            if(location.world.name !in listOf("star", "DIM1"))return false
            //location이 worldguard 중 어느 하나 안에 있으면 성공
            for(castle in SiegeCastle.DATA.values){
                if(location.world != castle.attackPosition.world)continue
                val set = WorldGuardPlugin.inst().getRegionManager(location.world)
                    .getApplicableRegions(location)?:continue
                val idSet = HashSet<String>()
                for(region in set.regions ?:continue){
                    idSet.add(region.id)
                }

                if(castle.worldGuardID in idSet)return false
            }
            return true
        }
    }
}
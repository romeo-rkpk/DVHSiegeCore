package com.danvhae.minecraft.siege.core.utils

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import org.bukkit.Location
import org.bukkit.entity.Player

class LocationUtil {
    companion object{
        fun worldGuardIds(location: Location):HashSet<String>{
            val set = WorldGuardPlugin.inst().getRegionManager(location.world).getApplicableRegions(location)
            val result = HashSet<String>()
            for(r in set.regions?:return result)
                result.add(r.id)
            if(PlayerUtil.isDistressZone(location))result.add(DVHSiegeCore.DISTRESS_ZONE_ID)

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

        /**
         * 이 플레이어가 공성 중인지 판단합니다
         */
        fun attacking(player: Player):Boolean{
            val sPlayer = SiegePlayer[player.uniqueId]?:return false
            for(castle in locationAtStars(player.location)){
                if(castle.status !in listOf(SiegeCastleStatus.UNDER_BATTLE, SiegeCastleStatus.PEACEFUL))continue
                if(castle.team != sPlayer.team){
                    return true
                }
            }
            return false
        }
    }
}
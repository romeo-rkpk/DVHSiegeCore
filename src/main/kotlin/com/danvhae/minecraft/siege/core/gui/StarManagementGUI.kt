package com.danvhae.minecraft.siege.core.gui

import com.danvhae.minecraft.siege.core.abstracts.WhiteBackgroundStarGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import org.bukkit.ChatColor
import org.bukkit.inventory.Inventory

class StarManagementGUI(override val castle:SiegeCastle, val siegePlayer: SiegePlayer) : WhiteBackgroundStarGUI(id,  castle){

    companion object{
        const val id = "별 관리"
        const val ROWS = 3
        const val STAR_LEVEL_SLOT = 11

        fun parse(inventory: Inventory, player:SiegePlayer):StarManagementGUI?{
            return StarManagementGUI(parseCastle(inventory)?:return null, player)

        }
    }

}
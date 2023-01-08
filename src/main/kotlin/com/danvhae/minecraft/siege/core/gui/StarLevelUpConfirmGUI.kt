package com.danvhae.minecraft.siege.core.gui

import com.danvhae.minecraft.siege.core.abstracts.WhiteBackgroundStarGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import org.bukkit.inventory.Inventory

class StarLevelUpConfirmGUI(override val castle:SiegeCastle, val siegePlayer: SiegePlayer)
                                                    : WhiteBackgroundStarGUI(id, castle){
    companion object{
        const val id = "별 레벨업"
        fun parse(inventory: Inventory, player:SiegePlayer):StarLevelUpConfirmGUI?{
            return StarLevelUpConfirmGUI(parseCastle(inventory)?:return null, player)
        }
    }


}
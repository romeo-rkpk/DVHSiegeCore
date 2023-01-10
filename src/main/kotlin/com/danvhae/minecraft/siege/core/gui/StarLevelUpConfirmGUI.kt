package com.danvhae.minecraft.siege.core.gui

import com.danvhae.minecraft.siege.core.abstracts.WhiteBackgroundStarGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.inventory.Inventory

class StarLevelUpConfirmGUI(override val castle:SiegeCastle, val siegePlayer: SiegePlayer)
                                                    : WhiteBackgroundStarGUI(id, castle){
    companion object{
        const val id = "별 레벨업"
        fun parse(inventory: Inventory, player:SiegePlayer):StarLevelUpConfirmGUI?{
            return StarLevelUpConfirmGUI(parseCastle(inventory)?:return null, player)
        }
    }

    override fun gui(): Inventory {
        val inventory = super.gui()

        val icon = inventory.getItem(starIconPosition)!!
        val meta = icon.itemMeta
        val prev = ArrayList(meta.lore)//.add(TextUtil.toColor("&f현재 레벨 : ${castle.level}"))
        prev.add(TextUtil.toColor("&f현재 레벨 : ${castle.level}"))
        meta.lore = prev.toList()
        icon.itemMeta = meta
        inventory.setItem(starIconPosition, icon)
        return inventory
    }


}
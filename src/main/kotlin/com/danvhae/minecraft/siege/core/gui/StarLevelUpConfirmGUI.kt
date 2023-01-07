package com.danvhae.minecraft.siege.core.gui

import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class StarLevelUpConfirmGUI(val castle:SiegeCastle, val siegePlayer: SiegePlayer) {
    companion object{
        const val id = "별 레벨업"
        const val STAR_ICON_SLOT = 4

        fun parse(inventory: Inventory, player:SiegePlayer):StarLevelUpConfirmGUI?{


            for(castle in SiegeCastle.DATA.values){
                if(ChatColor.stripColor(inventory.getItem(STAR_ICON_SLOT).itemMeta.displayName) == castle.name){
                    return StarLevelUpConfirmGUI(castle, player)
                }
            }

            return null
        }
    }

    fun gui(): Inventory {
        val inventory = DVHStaticGUI[id]!!.createInventory()

        //var stack: ItemStack = ItemStack(Material.STAINED_GLASS_PANE, 1, 0)
        for(i in 0 until inventory.size)
            inventory.setItem(i, ItemStack(Material.STAINED_GLASS_PANE, 1, 0)) //white glass pane

        for(button in DVHStaticGUI[StarShopGUI.id]!!.buttons){
            if(ChatColor.stripColor(TextUtil.toColor(button.name)) != castle.name)continue
            inventory.setItem(STAR_ICON_SLOT, button.toItemStack())
            break


        }

        for(button in DVHStaticGUI[id]!!.buttons)
            inventory.setItem(button.slot, button.toItemStack())

        return inventory

    }
}
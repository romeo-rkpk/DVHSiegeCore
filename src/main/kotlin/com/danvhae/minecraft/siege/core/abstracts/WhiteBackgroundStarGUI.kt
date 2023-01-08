package com.danvhae.minecraft.siege.core.abstracts

import com.danvhae.minecraft.siege.core.gui.StarShopGUI
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class WhiteBackgroundStarGUI(val id:String, open val castle: SiegeCastle, open val starIconPosition:Int = STAR_ICON_DEFAULT_SLOT) {

    companion object{
        const val STAR_ICON_DEFAULT_SLOT = 4

        @JvmStatic
        protected fun parseCastle(inventory: Inventory, starIconSlot:Int = STAR_ICON_DEFAULT_SLOT) : SiegeCastle?{
            for(castle in SiegeCastle.DATA.values){
                if(ChatColor.stripColor(inventory.getItem(starIconSlot).itemMeta.displayName) == castle.name){
                    return castle
                }
            }

            return null
        }
    }

    open fun gui(): Inventory {
        val inventory = DVHStaticGUI[id]!!.createInventory()
        ItemStack(Material.STAINED_GLASS_PANE, 1, 0).let {
            for(i in 0 until inventory.size)
                inventory.setItem(i, it)
        }

        for(button in DVHStaticGUI[StarShopGUI.id]!!.buttons){
            if(ChatColor.stripColor(TextUtil.toColor(button.name)) != castle.name)continue
            inventory.setItem(starIconPosition, button.toItemStack())
            break
        }

        for(button in DVHStaticGUI[id]!!.buttons){
            inventory.setItem(button.slot, button.toItemStack())
        }

        return inventory
    }

}
package com.danvhae.minecraft.siege.core.gui

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class StarShopGUI {
    companion object{
        const val id = "은하"
        fun gui(): Inventory {
            val inventory = DVHStaticGUI[id]!!.createInventory()
            for(i in 0 until inventory.size)
                inventory.setItem(i, ItemStack(Material.STAINED_GLASS_PANE, 1, if(i % 2 == 0) 0 else 4))

            val cover = DVHStaticGUI[id]!!
            for(button in cover.buttons)
                inventory.setItem(button.slot, button.toItemStack())

            for(stack in inventory){
                stack?:continue
                val meta = stack.itemMeta
                castle@for(castle in SiegeCastle.DATA.values){
                    if(castle.name != ChatColor.stripColor(meta.displayName))continue
                    val lore = listOf(meta.lore[0],
                        TextUtil.toColor(when(castle.status){
                            SiegeCastleStatus.PEACEFUL, SiegeCastleStatus.UNDER_BATTLE ->{
                                "&6&l매진"
                            }
                            SiegeCastleStatus.INIT->"&f&l구매 가능"

                            SiegeCastleStatus.ELIMINATED->"&4&l멸망"
                        })
                    )
                    meta.lore = lore
                    stack.itemMeta = meta
                    break@castle
                }
            }
            return inventory

        }
    }
}
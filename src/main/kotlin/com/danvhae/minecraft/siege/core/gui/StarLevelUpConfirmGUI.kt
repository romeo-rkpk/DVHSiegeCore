package com.danvhae.minecraft.siege.core.gui

import com.danvhae.minecraft.siege.core.abstracts.WhiteBackgroundStarGUI
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.ChatColor
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

        /*
        val meta = event.currentItem.itemMeta?:return
        val displayName = ChatColor.stripColor(meta.displayName)?:return
        val (requestedStr) = regex.find(displayName)!!.destructured

        //Bukkit.getLogger().warning(requestedStr)
        val requestedLevel = requestedStr.toIntOrNull()?:return
         */
        for(button in DVHStaticGUI[id]!!.buttons){
            val stack = button.toItemStack()
            val meta1 = stack.itemMeta?:continue
            val displayName = ChatColor.stripColor(meta1.displayName)?:continue
            val (level) = Regex("(\\d+)레벨").find(displayName)?.destructured?:continue
            meta1.lore = listOf(TextUtil.toColor(when(level.toInt()){
                in 0 .. castle.level -> "&f이미 달성한 레벨입니다"
                castle.level + 1 -> "&f신청 가능한 레벨입니다."
                else -> "&c지금은 신청할 수 없는 레벨입니다."
            }))
            stack.itemMeta = meta1
            inventory.setItem(button.slot, stack)
        }
        return inventory
    }


}
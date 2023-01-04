package com.danvhae.minecraft.siege.core.gui

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.objects.SiegeTeam
import com.danvhae.minecraft.siege.core.utils.NumberUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class StarBuyConfirmGUI(val castle:SiegeCastle, val player: Player) {
    val price:Int?
    init {
        val team = SiegeTeam.DATA[SiegePlayer.DATA[player.uniqueId]!!.team]!!
        var count = 0
        for(castle in team.castles()){
            if(castle.status == SiegeCastleStatus.INIT)continue
            count++
        }

        price = when(count){
            0 -> 500_0000
            1 -> 1000_0000
            2 -> 3000_0000
            else -> null
        }
    }
    companion object{
        const val id = "성주 계약을 맺으시겠습니까?"
        const val ABORT_ICON_POSITION = 10
        const val ACCEPT_ICON_POSITION = 16
        const val STAR_ICON_POSITION = 13

        const val PRICE_ICON_POSITION = 22

        fun parse(inventory: Inventory, player: Player):StarBuyConfirmGUI?{
            for(castle in SiegeCastle.DATA.values){
                if(ChatColor.stripColor(inventory.getItem(STAR_ICON_POSITION).itemMeta.displayName) == castle.name){
                    return StarBuyConfirmGUI(castle, player)
                }
            }

            return null
        }

    }

    fun gui():Inventory{
        val inventory = DVHStaticGUI[id]!!.createInventory()
        for(row in 0.. 2){
            for(col in 0 .. 3){
                val redStack = ItemStack(Material.STAINED_GLASS_PANE, 1, 14)
                val limeStack = ItemStack(Material.STAINED_GLASS_PANE, 1, 5)

                inventory.setItem(row * 9 + col, redStack)
                inventory.setItem(row * 9 + (8 - col), limeStack)
            }
        }

        val priceStack = ItemStack(Material.EMERALD)
        val priceMeta = priceStack.itemMeta
        priceMeta.displayName = TextUtil.toColor("&f&l가격")

        priceMeta.lore = if(price == null){
            listOf(TextUtil.toColor("&c구매 불가"), TextUtil.toColor("&7&o3개로 만족하십시오 휴먼. - 익명을 요구한 별"))
        }else{
            listOf(TextUtil.toColor("&f&l${price}스타"), TextUtil.toColor("&7&o${NumberUtil.numberToHangul(price, true)}"))
        }

        priceStack.itemMeta = priceMeta
        inventory.setItem(PRICE_ICON_POSITION, priceStack)

        val cover = DVHStaticGUI[id]!!
        for(button in cover.buttons)
            inventory.setItem(button.slot, button.toItemStack())

        for(button in DVHStaticGUI[StarShopGUI.id]!!.buttons){
            if(ChatColor.stripColor(TextUtil.toColor(button.name)) != castle.name)continue
            inventory.setItem(STAR_ICON_POSITION, button.toItemStack())
        }

        return inventory
    }
}
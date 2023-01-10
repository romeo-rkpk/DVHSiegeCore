package com.danvhae.minecraft.siege.core.listeners.guis

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.events.CastleLevelUpRequestedEvent
import com.danvhae.minecraft.siege.core.gui.StarLevelUpConfirmGUI
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class StarLevelUpGUIListener :Listener{

    companion object{
        private val regex = Regex("(\\d+)레벨")
    }

    @EventHandler
    fun onClickGUI(event:InventoryClickEvent){
        val gui = DVHStaticGUI.parseGUI(event.inventory)?:return
        if(gui.id() != StarLevelUpConfirmGUI.id)return
        if(event.rawSlot !in 0 until gui.rows * 9)return
        event.isCancelled = true


        event.currentItem?:return
        if(DVHSiegeCore.masterConfig.period)return

        //GUI 객체로 파싱하고 생각하기
        val player = event.whoClicked as Player
        val sPlayer = SiegePlayer[player.uniqueId]?:return
        if(!sPlayer.isOwner)return
        val levelUpGUI = StarLevelUpConfirmGUI.parse(event.inventory, sPlayer)?:return


        /*
        val (requestedStr) = (event.currentItem.itemMeta.displayName?:return).let {
            return@let regex.find(ChatColor.stripColor(it))!!.destructured
        }

         */
        val meta = event.currentItem.itemMeta?:return
        val displayName = ChatColor.stripColor(meta.displayName)?:return
        val (requestedStr) = regex.find(displayName)!!.destructured

        //Bukkit.getLogger().warning(requestedStr)
        val requestedLevel = requestedStr.toIntOrNull()?:return
        val castle = levelUpGUI.castle
        if(castle.team != sPlayer.team)return

        if(castle.level >= requestedLevel)return
        else if(requestedLevel - castle.level > 1)return

        //돈 빼 간 다음에
        castle.level = requestedLevel
        val price = when(requestedLevel){
            1 -> 10000
            2 -> 20000
            3 -> 30000
            else -> return
        }

        val eco = DVHSiegeCore.economy!!
        if(eco.getBalance(player).toInt() < price){
            player.closeInventory()
            player.sendMessage("스타가 부족합니다")
            return
        }
        eco.withdrawPlayer(player, price.toDouble())
        Bukkit.getPluginManager().callEvent(CastleLevelUpRequestedEvent(
            sPlayer, castle, requestedLevel, price
        ))
        player.closeInventory()
        player.sendMessage("별 레벨업 결제가 완료되었습니다.")
    }
}
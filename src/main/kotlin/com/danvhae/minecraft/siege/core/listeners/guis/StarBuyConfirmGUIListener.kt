package com.danvhae.minecraft.siege.core.listeners.guis

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.events.CastleBuyEvent
import com.danvhae.minecraft.siege.core.events.CastleDataChangedEvent
import com.danvhae.minecraft.siege.core.gui.StarBuyConfirmGUI
import com.danvhae.minecraft.siege.core.gui.StarShopGUI
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.EconomyUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class StarBuyConfirmGUIListener :Listener{

    @EventHandler
    fun onGUIClicked(event:InventoryClickEvent){
        val gui = DVHStaticGUI.parseGUI(event.inventory)?:return
        if(gui.id() != StarBuyConfirmGUI.id)return
        if(event.rawSlot !in 0 until gui.rows * 9)return
        event.isCancelled = true
        val player = event.whoClicked as Player

        if(event.rawSlot == StarBuyConfirmGUI.ABORT_ICON_POSITION)
            player.closeInventory()
        else if(event.rawSlot == StarBuyConfirmGUI.ACCEPT_ICON_POSITION){
            val parsedGUI = StarBuyConfirmGUI.parse(event.inventory, player)!!
            val castle = parsedGUI.castle
            if(parsedGUI.price == null)return
            if(parsedGUI.price > EconomyUtil.balance(player))return
            EconomyUtil.withDraw(player, parsedGUI.price, "${castle.name} 구매", DVHSiegeCore.instance!! )
            castle.team = SiegePlayer.DATA[player.uniqueId]!!.team
            castle.status = SiegeCastleStatus.PEACEFUL
            Bukkit.getPluginManager().callEvent(CastleBuyEvent(SiegePlayer[player.uniqueId]!!, parsedGUI.price, castle))
            player.closeInventory()
        }
    }

    @EventHandler
    fun onUpdated(event:CastleDataChangedEvent){
        for(player in Bukkit.getOnlinePlayers()){
            val inventory = player.openInventory?.topInventory?:continue
            val gui = DVHStaticGUI.parseGUI(inventory)?:continue
            if(gui.id() != StarBuyConfirmGUI.id)continue
            if(StarBuyConfirmGUI.parse(inventory, player)!!.castle.id == event.id){
                if(event.nowTeam != null){
                    player.closeInventory()
                    player.openInventory(StarShopGUI.gui())
                }
            }
        }
    }

}
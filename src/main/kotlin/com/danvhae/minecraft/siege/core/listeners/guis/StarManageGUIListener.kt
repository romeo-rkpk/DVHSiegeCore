package com.danvhae.minecraft.siege.core.listeners.guis

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.gui.StarManagementGUI
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class StarManageGUIListener : Listener {

    companion object{
        const val TICKET_SLOT = 15
    }

    @EventHandler
    fun onClick(event:InventoryClickEvent){
        /*
        val gui = DVHStaticGUI.parseGUI(event.inventory)?:return
        if(gui.id() != StarShopGUI.id)return

        if(event.rawSlot !in 0 until gui.rows * 9)return
        event.isCancelled = true

         */

        val gui = DVHStaticGUI.parseGUI(event.inventory)?:return
        if(gui.id() != StarManagementGUI.id) return
        if(event.rawSlot !in 0 until gui.rows * 9)return
        event.isCancelled = true

        val player = event.whoClicked as Player
        val sPlayer = SiegePlayer[player.uniqueId]?:return
        val managementGUI = StarManagementGUI.parse(event.inventory, sPlayer)?:return
        val castle = managementGUI.castle
        if(castle.status !in listOf(SiegeCastleStatus.PEACEFUL, SiegeCastleStatus.UNDER_BATTLE))return
        if(castle.team != sPlayer.team) return

        if(event.rawSlot == TICKET_SLOT){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ticket-item ${castle.id} work ${player.name}").let {
                return@let if(it) "티켓 지급 완료" else "티켓 지급 중 문제 발생, 인벤토리 여유공간이 충분한가요?"
            }.let{
                player.sendMessage(it)
            }
            player.closeInventory()
            return
        }



    }
}
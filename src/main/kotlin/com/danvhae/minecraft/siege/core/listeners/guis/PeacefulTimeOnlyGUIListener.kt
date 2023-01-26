package com.danvhae.minecraft.siege.core.listeners.guis

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.events.SiegeStartEvent
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent

/**
 * 공성 중 작동해서는 안 되는 GUI에 대하여 두 가지 기능을 제공합니다.
 * 1. 이미 GUI를 열어 두었다면 닫아버립니다.
 * 2. GUI를 새롭게 열면 없던 일로 만들어 버립니다.
 * @property id 공성시간 중에는 작동해서는 안 되는 gui의 id를 주십시오
 */
class PeacefulTimeOnlyGUIListener(private val id:String) : Listener{
    @EventHandler
    fun onSiegeStarted(event:SiegeStartEvent){
        if(!DVHSiegeCore.masterConfig.period)return
        for (player in Bukkit.getOnlinePlayers()) {
            val inventory = player.openInventory?.topInventory?:continue
            val gui = DVHStaticGUI.parseGUI(inventory)?:continue
            if(gui.id() != id)continue
            player.closeInventory()
            player.sendMessage("공성 중엔 사용할 수 없습니다.")
        }
    }

    @EventHandler
    fun onDuringSiege(event:InventoryOpenEvent){
        if(!DVHSiegeCore.masterConfig.period)return
        val gui = DVHStaticGUI.parseGUI(event.inventory)?:return
        if(gui.id() != id)return
        event.isCancelled = true
        event.player.sendMessage("공성 중엔 사용할 수 없습니다.")
    }
}
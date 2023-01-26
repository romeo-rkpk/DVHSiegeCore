package com.danvhae.minecraft.siege.core.listeners.guis

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.events.CastleDataChangedEvent
import com.danvhae.minecraft.siege.core.gui.StarBuyConfirmGUI
import com.danvhae.minecraft.siege.core.gui.StarShopGUI
import com.danvhae.minecraft.siege.core.objects.DVHGUIButton
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class StarShopGUIListener :Listener{

    @EventHandler
    fun onGUIClick(event:InventoryClickEvent){
        val gui = DVHStaticGUI.parseGUI(event.inventory)?:return
        if(gui.id() != StarShopGUI.id)return

        if(event.rawSlot !in 0 until gui.rows * 9)return
        event.isCancelled = true

        var button:DVHGUIButton? = null
        for(b in gui.buttons){
            if(b.slot == event.rawSlot){
                button = b
                break
            }
        }
        if(button == null)return
        SiegePlayer.DATA[event.whoClicked.uniqueId].let {
            if(it == null)return
            if(!it.isOwner)return
        }
        for(castle in SiegeCastle.DATA.values){
            if(ChatColor.stripColor(TextUtil.toColor(button.name)) != castle.name)continue
            if(castle.status != SiegeCastleStatus.INIT)return
            event.whoClicked.closeInventory()
            event.whoClicked.openInventory(StarBuyConfirmGUI(castle, event.whoClicked as Player).gui())
            return
        }
    }

    @EventHandler
    fun onCastleChanged(event:CastleDataChangedEvent){
        if(event.prevStatus != SiegeCastleStatus.INIT)return
        val gui = DVHStaticGUI[StarShopGUI.id]!!
        //val created = gui.createInventory()
        for(player in Bukkit.getOnlinePlayers()){
            val inventory = player.openInventory?.topInventory?:continue
            DVHStaticGUI.parseGUI(inventory)?.let {
                if(it.id() == StarShopGUI.id){
                    val nextGUI = StarShopGUI.gui()
                    for(i in 0 until 9 * it.rows)
                        inventory.setItem(i, nextGUI.getItem(i))
                }
            }?:continue
        }
    }

}
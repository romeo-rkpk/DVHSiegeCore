package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.events.DistressEndEvent
import com.danvhae.minecraft.siege.core.events.DistressStartEvent
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.UUID

class DistressListener : Listener {
    companion object{
        private val TASK_ID = HashMap<UUID, Int>()
    }

    @EventHandler
    fun onStart(event:DistressStartEvent){
        TASK_ID[event.player.uniqueId] = Bukkit.getScheduler().scheduleSyncRepeatingTask(DVHSiegeCore.instance!!, {
            TextUtil.sendActionBar(event.player, "&c[&f조난&c] &f전투 구역을 이탈하였습니다.")
        }, 0L, 40L)
    }

    @EventHandler
    fun onEnd(event:DistressEndEvent){
        TASK_ID[event.player.uniqueId]?.let{
            Bukkit.getScheduler().cancelTask(it)
        }?:return
        TASK_ID.remove(event.player.uniqueId)
    }
}
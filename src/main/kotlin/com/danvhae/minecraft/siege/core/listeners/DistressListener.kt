package com.danvhae.minecraft.siege.core.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.events.DistressEndEvent
import com.danvhae.minecraft.siege.core.events.DistressStartEvent
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.UUID

class DistressListener : Listener {
    companion object{
        private val TASK_ID = HashMap<UUID, Int>()
        private val COUNTER = HashMap<UUID, Int>()
    }

    @EventHandler
    fun onStart(event:DistressStartEvent){
        SiegePlayer[event.player.uniqueId]?:return
        COUNTER[event.player.uniqueId] = 0
        TASK_ID[event.player.uniqueId] = Bukkit.getScheduler().scheduleSyncRepeatingTask(DVHSiegeCore.instance!!, {
            val boldPrefix = if(COUNTER[event.player.uniqueId]!! % 2  == 0) "&l" else ""
            TextUtil.sendActionBar(event.player,
                    "&c${boldPrefix}[&f${boldPrefix}조난&c${boldPrefix}] &f${boldPrefix}전투 구역을 이탈하였습니다.")
            if(COUNTER[event.player.uniqueId] == 5)
                event.player.sendTitle(TextUtil.toColor("&c전투 구역 이탈"), "", 10, 70, 5)
            COUNTER[event.player.uniqueId] = (COUNTER[event.player.uniqueId]!! + 1) % 10
        }, 0L, 40L)
    }

    @EventHandler
    fun onEnd(event:DistressEndEvent){
        TASK_ID[event.player.uniqueId]?.let{
            Bukkit.getScheduler().cancelTask(it)
        }?:return
        TASK_ID.remove(event.player.uniqueId)
        COUNTER.remove(event.player.uniqueId)
    }
}
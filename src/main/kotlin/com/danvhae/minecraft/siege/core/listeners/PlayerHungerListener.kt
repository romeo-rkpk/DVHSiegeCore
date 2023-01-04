package com.danvhae.minecraft.siege.core.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class PlayerHungerListener : Listener {

    @EventHandler
    fun onPlayerHungry(event:FoodLevelChangeEvent){
        event.isCancelled = true
    }
}
package com.danvhae.minecraft.siege.events

import com.danvhae.minecraft.siege.abstracts.KotlinBukkitEventAbstract
import org.bukkit.entity.Player
import java.time.LocalDateTime

class DistressStartEvent(val player:Player) : KotlinBukkitEventAbstract(){
    val timestamp = LocalDateTime.now()
}
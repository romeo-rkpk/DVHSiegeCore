package com.danvhae.minecraft.siege.core.events

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import org.bukkit.entity.Player
import java.time.LocalDateTime

class DistressEndEvent(val player:Player) : KotlinBukkitEventAbstract() {
    val timestamp = LocalDateTime.now()
}
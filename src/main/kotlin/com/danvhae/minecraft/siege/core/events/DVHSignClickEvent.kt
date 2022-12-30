package com.danvhae.minecraft.siege.core.events

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import org.bukkit.block.Sign
import org.bukkit.event.player.PlayerInteractEvent

class DVHSignClickEvent(val sign: Sign, val originEvent:PlayerInteractEvent) :KotlinBukkitEventAbstract(){


    val player = originEvent.player

}
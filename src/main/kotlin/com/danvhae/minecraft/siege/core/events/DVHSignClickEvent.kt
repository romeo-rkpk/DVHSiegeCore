package com.danvhae.minecraft.siege.core.events

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import org.bukkit.block.Sign
import org.bukkit.event.player.PlayerInteractEvent

class DVHSignClickEvent(val sign: Sign, val originEvent:PlayerInteractEvent) :KotlinBukkitEventAbstract(){

   val lines:ArrayList<String> = object : ArrayList<String>() {
       override operator fun set(index: Int, element: String): String {
           sign.lines[index] = element
           sign.update()
           return element
       }
   }

}
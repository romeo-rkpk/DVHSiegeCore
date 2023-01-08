package com.danvhae.minecraft.siege.core.events

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import java.time.LocalDateTime

class SiegeEndEvent : KotlinBukkitEventAbstract(){
    val time = LocalDateTime.now()
}
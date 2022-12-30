package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import com.danvhae.minecraft.siege.core.objects.SiegeTeam

class LastCastleEliminateEvent(val killer:SiegeTeam, val victim:SiegeTeam) : KotlinBukkitEventAbstract() {
}
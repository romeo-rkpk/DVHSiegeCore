package com.danvhae.minecraft.siege.core.events

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer

class CastleEliminatedEvent(val castle:SiegeCastle, val killer:SiegePlayer, val protector:String) : KotlinBukkitEventAbstract()
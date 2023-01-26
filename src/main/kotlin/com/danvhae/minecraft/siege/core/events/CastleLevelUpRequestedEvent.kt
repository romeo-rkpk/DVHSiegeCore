package com.danvhae.minecraft.siege.core.events

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer

class CastleLevelUpRequestedEvent(val sPlayer:SiegePlayer, val castle:SiegeCastle, val requested:Int, val price:Int) : KotlinBukkitEventAbstract()
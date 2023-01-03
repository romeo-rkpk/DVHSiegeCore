package com.danvhae.minecraft.siege.core.events

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer

class CastleBuyEvent(val siegePlayer: SiegePlayer, val castle:SiegeCastle) : KotlinBukkitEventAbstract()
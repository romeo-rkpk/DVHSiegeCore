package com.danvhae.minecraft.siege.core.events

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus

class CastleDataChangedEvent(val id:String, val prevStatus:SiegeCastleStatus, val nowStatus: SiegeCastleStatus,
    val prevTeam:String?, val nowTeam:String?, val prevLevel:Int, val nowLevel:Int

) : KotlinBukkitEventAbstract() {

}
package com.danvhae.minecraft.siege.abstracts

import com.danvhae.minecraft.siege.objects.SiegeCastle

abstract class SiegeTeam(val name:String, val leader:SiegePlayer){
    abstract fun players():List<SiegePlayer>
    abstract fun addPlayer(player: SiegePlayer)
    abstract fun removePlayer(player: SiegePlayer)

    abstract fun castles():List<SiegeCastle>
    abstract fun aliveCastle():List<SiegeCastle>
}
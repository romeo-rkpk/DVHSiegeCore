package com.danvhae.minecraft.siege.abstracts

abstract class SiegeTeam(val name:String, val leader:SiegePlayer){
    abstract fun players():List<SiegePlayer>
    abstract fun addPlayer(player: SiegePlayer)
    abstract fun removePlayer(player: SiegePlayer)
}
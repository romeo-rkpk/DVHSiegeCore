package com.danvhae.minecraft.siege.abstracts

import org.bukkit.entity.Player

abstract class SiegePlayer(var team: SiegeTeam, var isOwner:Boolean, val alias:String?) {
    abstract fun toPlayer():Player

}
package com.danvhae.minecraft.siege.abstracts

import org.bukkit.entity.Player
import java.util.UUID

abstract class SiegePlayer(val playerUUID:UUID, var team: SiegeTeam, var isOwner:Boolean, val alias:String?) {
    abstract fun toPlayer():Player


}
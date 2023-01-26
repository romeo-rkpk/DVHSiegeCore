package com.danvhae.minecraft.siege.core.completers

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class CastleManageCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?
    ): MutableList<String> {
        args?:return arrayListOf()
        if(args.size != 1)return arrayListOf()
        val sPlayer = SiegePlayer[(sender as? Player?:return arrayListOf()).uniqueId]?:return arrayListOf()
        val result = ArrayList<String>()
        for(castle in SiegeCastle.DATA.values){
            if(castle.status !in listOf(SiegeCastleStatus.PEACEFUL, SiegeCastleStatus.UNDER_BATTLE))
                continue
            if(castle.team != sPlayer.team)
                continue
            result.add(castle.name)
            result.add(castle.id)
        }
        return TextUtil.onlyStartsWith(result, args.last())
    }
}
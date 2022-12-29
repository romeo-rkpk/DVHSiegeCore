package com.danvhae.minecraft.siege.core.completers

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegeTeam
import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.google.common.collect.Lists
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class CastleDataCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?
    ): MutableList<String> {
        sender?:return arrayListOf(); args?:return arrayListOf()
        if(!PermissionUtil.supportTeamOrConsole(sender))return arrayListOf()
        val result = ArrayList<String>()
        if (args.size == 1) {
            result.addAll(Lists.newArrayList("help", "list", "edit", "file", "tp-work", "tp-attack"))
            for(castle in SiegeCastle.DATA.values){
                result.add(castle.id)
            }
        } else if (args.size == 2) {
            if (args[0].equals("file", ignoreCase = true)) {
                result.addAll(Lists.newArrayList("load", "init", "testInit"))
            } else if (args[0].equals("edit", ignoreCase = true) || args[0].equals(
                    "tp-attack",
                    ignoreCase = true
                ) || args[0].equals("tp-work", ignoreCase = true)
            ) {
                for(castle in SiegeCastle.DATA.values){
                    result.add(castle.id)
                }
            }
        } else if (args.size == 3) {
            if (args[0].equals("edit", ignoreCase = true)) {
                result.addAll(Lists.newArrayList("worldGuardId", "status", "owner", "level", "tp-attack", "tp-work"))
            } else if (args[0].equals("tp", ignoreCase = true)) {
                Bukkit.getOnlinePlayers().forEach { p: Player? -> result.add(p!!.name) }
            }
        } else if (args.size == 4) {
            if (args[0].equals("edit", ignoreCase = true)) {
                if (args[2].equals("status", ignoreCase = true)) {
                    for(status in SiegeCastleStatus.values()){
                        result.add(status.toString())
                    }
                } else if (args[2].equals("team", ignoreCase = true)) {
                    for(team in SiegeTeam.DATA.values){
                        result.add(team.name)
                    }
                } else if (args[2].equals("level", ignoreCase = true)) {
                    result.addAll(Lists.newArrayList("0", "1", "2", "3"))
                } else if (args[2].equals("tp-attack", ignoreCase = true) || args[2].equals(
                        "tp-work",
                        ignoreCase = true
                    )
                ) {
                    result.add("here")
                }
            }
        }
        return TextUtil.onlyStartsWith(result, args.last())
    }
}
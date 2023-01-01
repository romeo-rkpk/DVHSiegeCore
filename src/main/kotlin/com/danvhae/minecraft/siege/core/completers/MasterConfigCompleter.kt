package com.danvhae.minecraft.siege.core.completers

import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class MasterConfigCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?
    ): MutableList<String> {
        if(sender == null || args == null)return arrayListOf()
        if(!PermissionUtil.supportTeamOrConsole(sender))return arrayListOf()
        val result = ArrayList<String>()
        if(args.size == 1){
            result.addAll(listOf("load", "save", "wildWorld", "period", "sirius", "meetingRoom"))
        }else if(args.size == 2){
            when(args[0]){
                "period", "sirius" -> result.addAll(listOf("true", "false"))
                "wildWorld" ->{
                    Bukkit.getWorlds().forEach{world -> result.add(world.name)}
                }

                "meetingRoom" ->{
                    result.add("set")
                }
            }
        }

        return TextUtil.onlyStartsWith(result, args.last())
    }
}
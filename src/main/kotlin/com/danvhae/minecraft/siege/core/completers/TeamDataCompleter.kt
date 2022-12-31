package com.danvhae.minecraft.siege.core.completers

import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TeamDataCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?,
        args: Array<out String>?
    ): MutableList<String> {
        if(sender == null || args == null)return arrayListOf()
        if(!PermissionUtil.supportTeamOrConsole(sender))return arrayListOf()
        val result = ArrayList<String>()
        if(args.size == 1){
            result.addAll(listOf("load"))
        }

        return TextUtil.onlyStartsWith(result, args.last())
    }
}
package com.danvhae.minecraft.siege.core.completers

import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class CastleManageCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?
    ): MutableList<String> {
        args?:return arrayListOf()
        if(args.size != 1)return arrayListOf()
        val result = ArrayList<String>()
        for(castle in SiegeCastle.DATA.values){
            result.add(castle.name)
            result.add(castle.id)
        }
        return TextUtil.onlyStartsWith(result, args.last())
    }
}
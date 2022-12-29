package com.danvhae.minecraft.siege.core.completers

import com.danvhae.minecraft.siege.core.objects.SiegeTeam
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class SiegePlayerDataCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?
    ): MutableList<String> {
        sender?:return arrayListOf();args?:return arrayListOf()
        if(args.isEmpty())return arrayListOf()
        val result = ArrayList<String>()

        when(args.size){
            1 ->{
                result.addAll(listOf("load", "info", "edit"))
            }

            3->{
                result.add("team")
            }
            4->{
                for(team in SiegeTeam.DATA.values){
                    result.add(team.name)
                }
            }
        }

        return TextUtil.onlyStartsWith(result, args.last())
    }
}
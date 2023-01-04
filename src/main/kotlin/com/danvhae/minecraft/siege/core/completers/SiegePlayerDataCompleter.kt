package com.danvhae.minecraft.siege.core.completers

import com.danvhae.minecraft.siege.core.objects.SiegeTeam
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.Bukkit
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
                result.addAll(listOf("load", "info", "edit", "remove", "add"))
            }
            2->{
                if(args[0] in listOf("info", "edit", "remove")){
                    for(p in Bukkit.getOnlinePlayers())
                        result.add(p.name)
                }else if(args[0] == "add"){
                    for(team in SiegeTeam.DATA.values){
                        result.add(team.name)
                    }
                }
            }
            3->{
                if(args[0] == "edit")
                    result.addAll(listOf("team", "alias"))
                else
                    for(p in Bukkit.getOnlinePlayers())
                        result.add(p.name)

            }
            4->{
                if(args[2] == "team") {
                    for (team in SiegeTeam.DATA.values) {
                        result.add(team.name)
                    }
                }
            }
        }

        return TextUtil.onlyStartsWith(result, args.last())
    }
}
package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.objects.WorldConfiguration
import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class WorldDataCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        if(sender == null || args == null)return false
        if(!PermissionUtil.supportTeamOrConsole(sender))return false

        if(args.isEmpty()){
            sender.sendMessage("world-config load")
            return false
        }

        if(args[0] == "load"){
            WorldConfiguration.load()
            sender.sendMessage("world data load")
        }

        return true
    }
}
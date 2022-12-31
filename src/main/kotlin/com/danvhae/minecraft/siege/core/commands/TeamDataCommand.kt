package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.objects.SiegeTeam
import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class TeamDataCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false; args?: return false
        if(!PermissionUtil.supportTeamOrConsole(sender))return false
        if(args.isEmpty()){
            sender.sendMessage("siege-team-data load")
            return false
        }

        if(args[0] == "load"){
            SiegeTeam.load()
            return true
        }

        return false
    }
}
package com.danvhae.minecraft.siege.core.utils

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender

class PermissionUtil {
    companion object{
        fun supportTeamOrConsole(sender:CommandSender):Boolean{
            return if(sender is ConsoleCommandSender){
                true
            }else{
                sender.hasPermission("siegecontrol.support")
            }
        }
    }
}
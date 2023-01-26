package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GUIDataCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false;args?:return false
        if(!PermissionUtil.supportTeamOrConsole(sender)) {
            sender.sendMessage("명령어를 실행할 권한이 없습니다.")
            return false
        }
        if(args.isEmpty())return false
        if(args[0] == "load") {
            sender.sendMessage("GUI 데이터를 불러왔습니다")
            DVHStaticGUI.load()
        }
        return true
    }
}
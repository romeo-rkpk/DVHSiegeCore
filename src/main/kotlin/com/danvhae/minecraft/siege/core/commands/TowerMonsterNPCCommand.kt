package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.events.TowerMonsterDeathEvent
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_12_R1.command.CraftRemoteConsoleCommandSender

class TowerMonsterNPCCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        if(sender == null || args == null)return false
        if(sender !is CraftRemoteConsoleCommandSender)return false
        if(args.isEmpty()){
            Bukkit.getLogger().warning("탑 몬스터 사망 명령어 실행 중 문제가 발생하였습니다. [args empty]")
            return false
        }

        Bukkit.getPluginManager().callEvent(TowerMonsterDeathEvent(args[0]))
        return true
    }
}
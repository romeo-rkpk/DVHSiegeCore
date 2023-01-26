package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.LocationUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MeetingRoomCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        val player = sender as? Player ?: return false
        if(LocationUtil.attacking(player)){
            player.sendMessage("공성 중에는 이 명령어를 사용할 수 없습니다.")
            return false
        }
        player.teleport(DVHSiegeCore.masterConfig.meetingRoom.toLocation()!!)
        return true
    }
}
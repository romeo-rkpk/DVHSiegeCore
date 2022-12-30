package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.NameUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_12_R1.command.CraftRemoteConsoleCommandSender

class StarCastleEliminateNPCCommand :CommandExecutor{
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        val craftSender = sender as? CraftRemoteConsoleCommandSender
        if(craftSender == null){
            sender?.sendMessage("이 명령어를 실행할 수 없습니다")
            return false
        }
        args?:return false

        if(args.size != 3){
            Bukkit.getLogger().info("명령어 인자 개수가 수상합니다")
            return false
        }

        val siegePlayer: SiegePlayer
        NameUtil.nameToUUID(args[0], true).let { uuid ->
            if(uuid == null){
                Bukkit.getLogger().warning("${args[0]}의  UUID 획득에 실패하였습니다!")
                return false
            }
            SiegePlayer.DATA[uuid].let { sPlayer ->
                if(sPlayer == null){
                    Bukkit.getLogger().warning("팀에 소속되지 아니한 자가 수호자를 죽인 것 같습니다!!")
                    return false
                }
                siegePlayer = sPlayer
            }
        }

        val castle = SiegeCastle.DATA[args[1].uppercase()]
        if(castle == null){
            Bukkit.getLogger().warning("${args[1]}은 올바르지 아니한 별 ID입니다!!")
            return false
        }

        TODO("Not yet implemented")

    }
}
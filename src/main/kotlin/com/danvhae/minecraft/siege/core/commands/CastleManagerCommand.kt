package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.gui.StarManagementGUI
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CastleManagerCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        val player = sender as? Player ?:return false
        args?:return false
        if(args.isEmpty()){
            player.sendMessage("/별관리 별이름")
            player.sendMessage("/별관리 별ID")
            return true
        }

        var castle:SiegeCastle? = null
        for(c in SiegeCastle.DATA.values){
            if(c.id == args[0] || c.name == args[0]){
                castle = c
                break
            }
        }

        if(castle == null){
            player.sendMessage("${args[0]}은(는) 올바르지 않은 별 ID 혹은 별 이름입니다")
            return false
        }

        val siegePlayer = SiegePlayer.DATA[player.uniqueId].let{
            if(it == null){
                player.sendMessage("귀하는 공성전에 참여하는 플레이어가 아닌 듯 합니다")
                return false
            }else{
                return@let it
            }
        }

        if(castle.team != siegePlayer.team){
            player.sendMessage("별이 접근을 거부하였습니다")
            player.sendMessage("해당 별을 소유하고 있는지 확인하여 주십시오")
            return false
        }

        StarManagementGUI(castle, SiegePlayer[player.uniqueId]?:return false).gui().let {
            player.openInventory(it)
        }

        return true
    }
}
package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.NameUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class SiegePlayerData : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false; args?:return false
        if(args.isEmpty()){
            sender.sendMessage("/player-data load")
            sender.sendMessage("/player-data info <NAME>")
            //sender.sendMessage("/player-data init <NAME>")
            //sender.sendMessage("/player-data delete <NAME>")
            sender.sendMessage("/player data edit <NAME> <Attribute> <Value>")
        }
        else if(args.size == 1){
            if(args[0] == "load"){
                SiegePlayer.load()
            }else return false
        }
        else if(args.size == 2){
            if(args[0] !in listOf("info", "init", "delete")){
                return false
            }
            sender.sendMessage("================================")
            Bukkit.getScheduler().runTask(DVHSiegeCore.instance){
                NameUtil.nameToUUID(args[1]).let {uuid->
                    if(uuid == null){
                        sender.sendMessage("올바르지 않은 플레이어 명입니다")
                        return@runTask
                    }

                    SiegePlayer.DATA[uuid].let{player->
                        if(player == null){
                            sender.sendMessage("해당 플레이어의 데이터가 존재하지 않습니다")
                            return@runTask
                        }
                        sender.sendMessage("%s님 공성 플레이어 정보------------------".format(NameUtil.uuidToName(uuid)!!))
                        sender.sendMessage("소속 : ${player.team}")
                        sender.sendMessage("성주 여부 : ${player.isOwner}")
                        sender.sendMessage("별칭 : ${player.alias}")
                        sender.sendMessage("UUID : ${player.playerUUID}")
                    }
                }
            }

            return true
        }else if(args.size == 5){
            if(args[0] != "edit"){
                return false
            }

            sender.sendMessage("=======================================")
            Bukkit.getScheduler().runTask(DVHSiegeCore.instance){
                val uuid = NameUtil.nameToUUID(args[1])
                if(uuid == null){
                    sender.sendMessage("올바르지 않은 플레이어 명입니다")
                    return@runTask
                }


            }

        }
        TODO("Not yet implemented")
    }
}
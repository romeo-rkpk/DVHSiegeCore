package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegeTeam
import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class CastleDataCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false; args?:return false
        if(!PermissionUtil.supportTeamOrConsole(sender)) {
            sender.sendMessage("명령어를 실행할 권한이 없습니다")
            return false
        }


        if (args.isEmpty()) {
            sendInfoMessage(sender)
            return true
        }
        if (args.size == 1) {
            return if (args[0].equals("help", ignoreCase = true)) {
                sendInfoMessage(sender)
                true
            } else if (args[0].equals("list", ignoreCase = true)) {
                sender.sendMessage("=================================")
                for(castle in SiegeCastle.DATA.values) {
                    sender.sendMessage("${castle.id} (${castle.name})")
                }
                sender.sendMessage("==================================")
                true
            }else{
                val castle = SiegeCastle.DATA[args[0]]?:return false
                sender.sendMessage("${castle.id}(${castle.name}) 정보")
                sender.sendMessage("상태 : ${castle.status}")
                sender.sendMessage("소유 : ${
                    castle.team.let {  
                        return@let if(it == null) "없음" else TextUtil.toColor("${SiegeTeam[it]!!.colorPrefix}${it}")
                    }
                }")
                sender.sendMessage("레벨 : ${castle.level}")
                true
            }
        } else if (args.size == 2) {
            if (args[0].equals("file", ignoreCase = true)) {
                return if (args[1].equals("load", ignoreCase = true)) {
                    SiegeCastle.load()
                    true
                }else false
            }
        } else if (args.size == 4) {
            if (args[0].equals("edit", ignoreCase = true)) {
                var castle = SiegeCastle.DATA[args[1]]
                if(castle == null){
                    sender.sendMessage("Invalid castle id")
                    return false
                }

                val attribute = args[2].lowercase(Locale.getDefault())
                val value = args[3]
                if (attribute == "worldGuardId".lowercase(Locale.getDefault())) {
                    castle.worldGuardID = value
                } else if (attribute == "status") {
                    val status:SiegeCastleStatus
                    try {
                        status = SiegeCastleStatus.valueOf(value)
                    }catch(_:Exception){
                        sender.sendMessage("invalid status")
                        return false
                    }

                    castle.status = status
                } else if (attribute == "team") {
                    var team: SiegeTeam? = SiegeTeam.DATA[value]
                    if(team == null) {
                        sender.sendMessage("invalid team id")
                        return false
                    }
                    castle.team = team.name
                } else if (attribute == "level") {
                    try {
                        val level = value.toInt()
                        if (level < 0 || level > 3) {
                            sender.sendMessage("레벨이 지정된 범위를 벗어났습니다")
                            return false
                        }
                        castle.level = level
                    } catch (e: IllegalArgumentException) {
                        return false
                    }
                }else if (attribute == "tp-attack") {
                    if (sender !is Player) {
                        sender.sendMessage("장소를 지정하려면 플레이어로 접속하십시오")
                        return false
                    }
                    if (value.equals("here", ignoreCase = true)) {
                        //castle.setAttackLocation(sender.location)
                        castle.attackPosition = sender.location
                        //StarCastle.update(castle.getId(), castle);
                    }
                } else if (attribute == "tp-work") {
                    if (sender !is Player) {
                        sender.sendMessage("장소를 지정하려면 플레이어로 접속하십시오")
                        return false
                    }
                    if (value.equals("here", ignoreCase = true)) {
                        //castle.setWorkerLocation(sender.location)
                        //StarCastle.update(castle.getId(), castle);
                        castle.workPosition = sender.location
                    }
                } else return false

                //StarCastle.update(castle.getId(), castle);
                SiegeCastle.save()
            }
        }

        /*
         * help
         * list
         * <id>
         * edit <id> <attribute> <value>
         * file load
         * file save
         * file init
         * file testinit
         */


        /*
         * help
         * list
         * <id>
         * edit <id> <attribute> <value>
         * file load
         * file save
         * file init
         * file testinit
         */return false
    }

    private fun sendInfoMessage(sender: CommandSender) {
        sender.sendMessage("---- help")
        sender.sendMessage("---- list")
        sender.sendMessage("---- <id>")
        sender.sendMessage("---- edit <id> <attribute> <value>")
        //sender.sendMessage("---- file <load/save/init/testInit>")
    }
}
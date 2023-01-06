package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.objects.LocationData
import com.danvhae.minecraft.siege.core.objects.MasterConfig
import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MasterConfigurationCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false; args?:return false
        if(!PermissionUtil.supportTeamOrConsole(sender))return false
        val config = DVHSiegeCore.masterConfig

        if(args.isEmpty()){
            sender.sendMessage("master-config save")
            sender.sendMessage("master-config load")
            sender.sendMessage("master-config wildWorld [<worldName>]")
            sender.sendMessage("master-config period [<value>]")
            sender.sendMessage("maste-config sirius [<value>]")
            return true
        }

        when(args[0]){
            "save"->MasterConfig.save(config)
            "load"->{
                MasterConfig.load().let {
                    config.wildWorldName = it.wildWorldName
                    config.meetingRoom = it.meetingRoom
                    config.sirius = it.sirius
                    config.period = it.period
                }
            }
            "wildWorld", "period", "sirius", "meetingRoom", "slaveStore" ->{
                if(args.size == 2){
                    when(args[0]){
                        "wildWorld" -> config.wildWorldName = args[1]
                        "meetingRoom", "slaveStore" -> config.meetingRoom = if(sender is Player){
                            if(args[1] == "set"){
                                LocationData(sender.location)
                            }else{
                                if(args[0] == "meetingRoom")
                                    config.meetingRoom
                                else
                                    config.slaveStore
                            }
                            }else{
                                sender.sendMessage("이 명령어로 ${if(args[0] == "meetingRoom") "회의실" else "노예상점"} 위치를 조정하려면 플레이어로 접속하세요")
                                if(args[0] == "meetingRoom")
                                    config.meetingRoom
                                else
                                    config.slaveStore
                            }


                        "period" -> args[1].toBooleanStrictOrNull().let { bool->
                            if(bool == null){
                                sender.sendMessage("${args[1]}은 올바르지 않은 값입니다")
                            }else{
                                config.period = bool
                            }
                        }

                        "sirius" -> args[1].toBooleanStrictOrNull().let{bool->
                            if(bool == null)
                                sender.sendMessage("올바르지 않은 값입니다")
                            else
                                config.sirius = bool
                        }
                    }
                }

                when(args[0]){
                    "wildWorld"->Pair("야생 월드 이름", config.wildWorldName)
                    "period" -> Pair("공성 티켓 활성화 여부", config.period.toString())
                    "sirius" -> Pair("시리우스 활성화 여부", config.sirius.toString())
                    "meetingRoom" -> Pair("회의실 위치", config.meetingRoom.toString())
                    "slaveStore" -> Pair("노예상점 위치", config.slaveStore.toString())
                    else -> null
                }?.let { (key, value) ->
                    sender.sendMessage("$key : $value")
                }
            }
        }

        return true
    }
}
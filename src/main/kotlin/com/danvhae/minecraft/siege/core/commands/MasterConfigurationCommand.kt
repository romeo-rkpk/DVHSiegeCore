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
        if(!PermissionUtil.supportTeamOrConsole(sender)) {
            sender.sendMessage("명령어를 실행할 권한이 없습니다")
            return false
        }
        val config = DVHSiegeCore.masterConfig

        if(args.isEmpty()){
            sender.sendMessage("master-config save")
            sender.sendMessage("master-config load")
            sender.sendMessage("master-config wildWorld [<worldName>]")
            sender.sendMessage("master-config period [<value>]")
            sender.sendMessage("master-config sirius [<value>]")
            return true
        }

        when(args[0]){
            "save"-> {
                MasterConfig.save(config)
                sender.sendMessage("설정 정보를 저장하였습니다")
            }
            "load"->{
                MasterConfig.load().let {
                    config.wildWorldName = it.wildWorldName
                    config.meetingRoom = it.meetingRoom
                    config.sirius = it.sirius
                    config.period = it.period
                    config.slaveStore = it.slaveStore
                }
                sender.sendMessage("설정 엊보를 불러왔습니다")
            }
            "wildWorld", "period", "sirius", "meetingRoom", "slaveStore" ->{
                if(args.size == 2){
                    when(args[0]){
                        "wildWorld" -> config.wildWorldName = args[1]

                        /*
                        "meetingRoom", "slaveStore" -> {
                            config.meetingRoom = if(sender is Player){
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
                        }

                         */
                        "meetingRoom", "slaveStore" ->{
                            if(args[1] == "set"){
                                if(sender !is Player){
                                    sender.sendMessage("플레이어만이 위치를 조정할 수 있습니다.")
                                    return false
                                }
                                if(args[0] == "meetingRoom"){
                                    config.meetingRoom = LocationData(sender.location)
                                }else{
                                    config.slaveStore = LocationData(sender.location)
                                }

                            }

                            /*
                            sender.sendMessage((if(args[0] == "meetingRoom") "회의실" else "노예상점") + "위치 : ${
                                if(args[0] == "meetingRoom") config.meetingRoom.toString() else config.slaveStore.toString()
                            }")

                             */
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
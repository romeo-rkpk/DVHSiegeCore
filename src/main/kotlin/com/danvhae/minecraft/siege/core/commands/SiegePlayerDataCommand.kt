package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.objects.SiegeTeam
import com.danvhae.minecraft.siege.core.utils.NameUtil
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class SiegePlayerDataCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false; args?:return false
        if(args.isEmpty()){
            sender.sendMessage("/player-data load")
            sender.sendMessage("/player-data info <NAME>")
            sender.sendMessage("/player-data add team NAME")
            sender.sendMessage("/player-data remove NAME")
            //sender.sendMessage("/player-data init <NAME>")
            //sender.sendMessage("/player-data delete <NAME>")
            sender.sendMessage("/player-data edit <NAME> <Attribute> <Value>")
        }
        else if(args.size == 1){
            if(args[0] == "load"){
                SiegePlayer.load()
            }else if(args[0] == "save"){
                SiegePlayer.save()
            }else return false
        }
        else if(args.size == 2){
            if(args[0] in listOf("info", "remove")) {
                sender.sendMessage("================================")
                Bukkit.getScheduler().runTask(DVHSiegeCore.instance) {
                    NameUtil.nameToUUID(args[1]).let { uuid ->
                        if (uuid == null) {
                            sender.sendMessage("올바르지 않은 플레이어 명입니다")
                            return@runTask
                        }

                        NameUtil.uuidToName(uuid)!!.let { name->
                            TextComponent("Name: $name").let { txtComp ->
                                txtComp.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "http://danvhae.com/${name}")
                                sender.spigot().sendMessage(txtComp)
                            }
                        }
                        TextComponent("UUID : $uuid").let { textComp ->
                            textComp.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "http://danvhae.com/${uuid}")
                            sender.spigot().sendMessage(textComp)
                        }



                        SiegePlayer[uuid].let { player ->
                            if (player == null) {
                                sender.sendMessage("해당 플레이어의 데이터가 존재하지 않습니다")
                                return@runTask
                            }

                            if(args[0] == "info") {
                                sender.sendMessage("%s님 공성 플레이어 정보------------------".format(NameUtil.uuidToName(uuid)!!))
                                sender.sendMessage("소속 : ${player.team}")
                                sender.sendMessage("성주 여부 : ${player.isOwner}")
                                sender.sendMessage("별칭 : ${player.alias}")
                                //sender.sendMessage("UUID : ${player.playerUUID}")

                            }else{
                                SiegePlayer.DATA.remove(uuid)
                                sender.sendMessage("${NameUtil.uuidToName(uuid, true)}님 데이터를 제거하였습니다.")
                            }
                        }
                    }
                }
            }else if(args[0] == "load"){
                SiegePlayer.load()
            }

            return true
        }else if(args.size == 3){
            if(args[0] == "add"){
                val team = SiegeTeam[args[1]]
                if(team == null){
                    sender.sendMessage("${args[1]}은 올바르지 않은 팀 이름입니다.")
                    return false
                }
                Bukkit.getScheduler().runTask(DVHSiegeCore.instance) {
                    val uuid = NameUtil.nameToUUID(args[2])
                    if(uuid == null){
                        sender.sendMessage("잘못된 플레이어 이름입니다")
                        return@runTask
                    }
                    if(uuid in SiegePlayer){
                        sender.sendMessage("그 사람은 이미 공성 플레이어로 등록되어 있습니다")
                        return@runTask
                    }

                    SiegePlayer.DATA[uuid] = SiegePlayer(uuid, team.name, false, null)
                }
            }
        }

        else if(args.size == 4){
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



                val player = SiegePlayer[uuid]
                if(player == null){
                    sender.sendMessage("존재하지 않는 플레이어입니다")
                    return@runTask
                }

                if(args[2] == "team"){
                    SiegeTeam[args[3]].let { team ->
                        if(team == null){
                            sender.sendMessage("존재하지 않는 팀입니다")
                            return@runTask
                        }

                        player.team = team.name
                    }
                }else if(args[2] == "alias"){
                    player.alias = args[3]
                }

                else{
                    sender.sendMessage("올바르지 않은 속성입니다")
                    return@runTask
                }


            }
            return true

        }
        return true
    }
}
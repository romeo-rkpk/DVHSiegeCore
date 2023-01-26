package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.max

class TimerCommand : CommandExecutor {
    companion object{
        private var target = LocalDateTime.now()
        private var startAt = LocalDateTime.now()
        private val bar = Bukkit.createBossBar("[공성 타이머 준비 중]", BarColor.BLUE, BarStyle.SEGMENTED_20, BarFlag.DARKEN_SKY)
        private var taskID:Int? = null
        private var prefix = ""
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd-HH:mm:ss")

        fun addPlayer(player: Player){
            if(taskID != null)
            bar.addPlayer(player)
        }

        fun removePlayer(player:Player){
            bar.removePlayer(player)
        }
    }
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        if(sender == null || args == null)return false
        if(!PermissionUtil.supportTeamOrConsole(sender)){
            sender.sendMessage("권한이 없습니다.")
            return false
        }

        if(args.isEmpty()){
            listOf(
                "/siege-timer title NAME",
                "/siege-timer time yyyy:MM:dd-HH:mm:ss",
                "/siege-timer start",
                //"/siege-timer pause",
                "/siege-timer stop"
            ).forEach { sender.sendMessage(it) }
            return true
        }

        when(args[0]){
            "title"->{
                if(args.size == 2){
                    prefix = TextUtil.toColor(args[1])
                }
                sender.sendMessage(prefix)
                return true
            }

            "time" ->{
                if(args.size == 2){
                    try{
                        target = LocalDateTime.parse(args[1], formatter)
                    }catch (_:Exception){
                        sender.sendMessage("error occurred")
                        return false
                    }
                    sender.sendMessage("target : ${target.format(formatter)}")
                    return true
                    //sender.sendMessage("${target. }")
                }
            }

            "start"->{
                if(taskID != null){
                    sender.sendMessage("이미 작동 중입니다")
                    return true
                }
                bar.title = ""
                bar.removeAll()
                Bukkit.getOnlinePlayers().forEach{bar.addPlayer(it)}
                startAt = LocalDateTime.now()
                taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(DVHSiegeCore.instance, {
                    val duration = Duration.between(LocalDateTime.now(), target)
                    var seconds = abs(duration.seconds)
                    var minutes = seconds / 60; seconds %= 60
                    val hours = minutes / 60; minutes %= 60


                    bar.title = prefix +
                            if(duration.seconds < 0)
                                TextUtil.toColor("시간 종료!! &f(+${- duration.seconds}초)")
                            else
                                TextUtil.toColor("&f[${hours}시간 ${minutes}분 ${seconds}초]")
                    bar.progress = max(0.0, duration.seconds.toDouble() / Duration.between(startAt, target).seconds)
                }, 0L, 20L)
            }

            "stop" ->{
                if(taskID == null){
                    sender.sendMessage("작동 중이 아닙니다")
                    return false
                }
                bar.removeAll()
                Bukkit.getScheduler().cancelTask(taskID!!)
                taskID = null
            }
        }




        return true
    }
}
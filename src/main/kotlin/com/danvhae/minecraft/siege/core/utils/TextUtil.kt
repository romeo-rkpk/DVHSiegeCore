package com.danvhae.minecraft.siege.core.utils

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class TextUtil {

    companion object{
        fun toColor(str:String):String{
            return ChatColor.translateAlternateColorCodes('&', str)
        }

        fun onlyStartsWith(args:ArrayList<String>, str:String) : ArrayList<String>{
            fun splitHangul(s:String):String{
                val buffer = StringBuffer()
                for(c in s){
                    val hangul = Hangul(c)
                    if(!hangul.isHangul()) {
                        buffer.append(c)
                        continue
                    }
                    for (it in listOf(hangul.first(), hangul.second(), hangul.last())) {
                        buffer.append(it?:continue)
                    }
                }
                return buffer.toString()
            }

            val splitStr = splitHangul(str.lowercase())
            args.removeIf{
                return@removeIf !splitHangul(it.lowercase()).startsWith(splitStr)
            }
            return args
        }

        fun sendActionBar(player: Player, msg:String){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(toColor(msg)))

        }


    }
}
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
            args.removeIf{s -> !s.lowercase().startsWith(str)}
            return args
        }

        fun sendActionBar(player: Player, msg:String){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(toColor(msg)))

        }


    }
}
package com.danvhae.minecraft.siege.core.commands

import com.danvhae.minecraft.siege.core.gui.StarShopGUI
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Deprecated("이 명령어는 테스트를 위해 작성되었음")
class GUITestCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        val player = sender as? Player?:return false;
        player.openInventory(StarShopGUI.gui())
        return true
    }
}
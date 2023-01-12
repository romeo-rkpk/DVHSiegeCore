package com.danvhae.minecraft.siege.core.utils

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class EconomyUtil {
    companion object{
        /*
         if(server.pluginManager.getPlugin("Vault") != null){
            val resp:RegisteredServiceProvider<Economy>? = server.servicesManager.getRegistration(Economy::class.java)
            economy = resp?.provider!!
        }
         */
        private val economy = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java).provider!!

        private fun log(type:String, description:String, amount:Int, instance:JavaPlugin){
            Bukkit.getLogger().info("[Vault Logging] $type $amount $description ${instance.name}")
        }

        fun withDraw(player: Player, amount:Int, description: String, instance: JavaPlugin){
            economy.withdrawPlayer(player, amount.toDouble())
            log("출금 - ${player.name}", description, amount, instance)
        }

        fun deposit(player: Player, amount: Int, description: String, instance: JavaPlugin){
            economy.depositPlayer(player, amount.toDouble())
            log("입금 - ${player.name}", description, amount, instance)
        }

        fun balance(player:Player): Int{
            return economy.getBalance(player).toInt()
        }
    }
}
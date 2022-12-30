package com.danvhae.minecraft.siege.core

import com.danvhae.minecraft.siege.core.commands.CastleDataCommand
import com.danvhae.minecraft.siege.core.commands.SiegePlayerDataCommand
import com.danvhae.minecraft.siege.core.completers.CastleDataCompleter
import com.danvhae.minecraft.siege.core.completers.SiegePlayerDataCompleter
import com.danvhae.minecraft.siege.core.listeners.CastleEliminatedListener
import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.objects.SiegeTeam
import com.danvhae.minecraft.siege.core.listeners.PlayerEnterRegionListener
import com.danvhae.minecraft.siege.core.listeners.PlayerLeaveRegionListener
import com.danvhae.minecraft.siege.core.listeners.PlayerMoveRegionListener
import com.danvhae.minecraft.siege.core.objects.MasterConfig
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.RegisteredServiceProvider
import org.bukkit.plugin.java.JavaPlugin

class DVHSiegeCore : JavaPlugin() {

    companion object{
        const val FOLDER_PATH = "plugins/DVHsiege"
        const val DISTRESS_ZONE_ID = "#DISTRESS"
        var masterConfig:MasterConfig = MasterConfig()
            private set

        var economy: Economy? = null
            get(){return field!!}
            private set

        var instance: DVHSiegeCore? = null
            get() {return field!!}
            private set

    }


    override fun onEnable() {
        instance = this
        FileUtil.initFolder()

        if(server.pluginManager.getPlugin("Vault") != null){
            val resp:RegisteredServiceProvider<Economy>? = server.servicesManager.getRegistration(Economy::class.java)
            economy = resp?.provider!!
        }

        Bukkit.getLogger().info("단츄 보라비 해야 화이팅!")

        val pm = Bukkit.getPluginManager()
        pm.registerEvents(PlayerMoveRegionListener(), this)
        pm.registerEvents(PlayerEnterRegionListener(), this)
        pm.registerEvents(PlayerLeaveRegionListener(), this)
        pm.registerEvents(CastleEliminatedListener(), this)

        SiegeCastle.load()
        SiegeTeam.load()
        SiegePlayer.load()

        getCommand("castle-data").executor = CastleDataCommand()
        getCommand("castle-data").tabCompleter = CastleDataCompleter()

        getCommand("player-data").executor = SiegePlayerDataCommand()
        getCommand("player-data").tabCompleter = SiegePlayerDataCompleter()

        masterConfig = MasterConfig.load()
    }
}
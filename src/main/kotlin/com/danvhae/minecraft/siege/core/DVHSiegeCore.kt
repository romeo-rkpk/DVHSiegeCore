package com.danvhae.minecraft.siege.core

import com.danvhae.minecraft.siege.core.commands.*
import com.danvhae.minecraft.siege.core.completers.CastleDataCompleter
import com.danvhae.minecraft.siege.core.completers.MasterConfigCompleter
import com.danvhae.minecraft.siege.core.completers.SiegePlayerDataCompleter
import com.danvhae.minecraft.siege.core.gui.StarBuyConfirmGUI
import com.danvhae.minecraft.siege.core.listeners.*
import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.danvhae.minecraft.siege.core.listeners.guis.StarBuyConfirmGUIListener
import com.danvhae.minecraft.siege.core.listeners.guis.StarShopGUIListener
import com.danvhae.minecraft.siege.core.objects.*
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
        pm.registerEvents(StarShopGUIListener(), this)
        pm.registerEvents(StarBuyConfirmGUIListener(), this)
        pm.registerEvents(PlayerSignClickListener(), this)
        pm.registerEvents(PlayerNameCacheUpdateListener(), this)


        SiegeCastle.load()
        SiegeTeam.load()
        SiegePlayer.load()
        DVHStaticGUI.load()

        getCommand("castle-data").executor = CastleDataCommand()
        getCommand("castle-data").tabCompleter = CastleDataCompleter()

        getCommand("player-data").executor = SiegePlayerDataCommand()
        getCommand("player-data").tabCompleter = SiegePlayerDataCompleter()
        getCommand("gui-data").executor = GUIDataCommand()

        getCommand("master-config").let { cmd ->
            cmd.executor = MasterConfigurationCommand()
            cmd.tabCompleter = MasterConfigCompleter()
        }

        getCommand("siege-team-data").let { cmd ->
            cmd.executor = TeamDataCommand()
        }


        getCommand("gui-test").executor = GUITestCommand()




        masterConfig = MasterConfig.load()
    }
}
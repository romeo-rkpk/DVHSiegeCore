package com.danvhae.minecraft.siege.core

import com.danvhae.minecraft.siege.core.commands.*
import com.danvhae.minecraft.siege.core.completers.*
import com.danvhae.minecraft.siege.core.gui.StarBuyConfirmGUI
import com.danvhae.minecraft.siege.core.gui.StarLevelUpConfirmGUI
import com.danvhae.minecraft.siege.core.gui.StarShopGUI
import com.danvhae.minecraft.siege.core.listeners.*
import com.danvhae.minecraft.siege.core.listeners.guis.*
import com.danvhae.minecraft.siege.core.objects.*
import com.danvhae.minecraft.siege.core.utils.FileUtil
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

        var instance: DVHSiegeCore? = null
            get() {return field!!}
            private set

    }


    override fun onEnable() {
        instance = this
        FileUtil.initFolder()

        /*
        if(server.pluginManager.getPlugin("Vault") != null){
            val resp:RegisteredServiceProvider<Economy>? = server.servicesManager.getRegistration(Economy::class.java)
            economy = resp?.provider!!
        }

         */

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
        pm.registerEvents(PlayerHungerListener(), this)
        pm.registerEvents(StarManageGUIListener(), this)
        pm.registerEvents(PlayerJoinListener(), this)
        pm.registerEvents(PlayerRespawnListener(), this)
        pm.registerEvents(StarLevelUpGUIListener(), this)
        pm.registerEvents(IllegalTPEnderWorldListener(), this)
        pm.registerEvents(PlayerQuitListener(), this)
        pm.registerEvents(DistressListener(), this)

        listOf(StarBuyConfirmGUI.id, StarShopGUI.id, StarLevelUpConfirmGUI.id).forEach{id ->
            pm.registerEvents(PeacefulTimeOnlyGUIListener(id), this)
        }


        SiegeOperator.load()
        SiegeCastle.load()
        SiegeTeam.load()
        SiegePlayer.load()
        DVHStaticGUI.load()
        WorldConfiguration.load()

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
            cmd.tabCompleter = TeamDataCompleter()
        }

        getCommand("멸망판정").executor = StarCastleEliminateNPCCommand()
        getCommand("tower-notify").executor = TowerMonsterNPCCommand()

        getCommand("회의실").executor = MeetingRoomCommand()

        getCommand("별관리").let { cmd->
            cmd.executor = CastleManagerCommand()
            cmd.tabCompleter = CastleManageCompleter()
        }

        getCommand("siege-timer").let { cmd->
            cmd.executor = TimerCommand()
            cmd.tabCompleter = TimerCompleter()
        }

        getCommand("world-config").let { cmd->
            cmd.executor = WorldDataCommand()
        }


        masterConfig = MasterConfig.load()

    }
}
package com.danvhae.minecraft.siege

import com.danvhae.minecraft.siege.listeners.PlayerEnterRegionListener
import com.danvhae.minecraft.siege.listeners.PlayerLeaveRegionListener
import com.danvhae.minecraft.siege.listeners.PlayerMoveRegionListener
import com.danvhae.minecraft.siege.utils.FileUtil
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class DVHSiegeCore : JavaPlugin() {

    companion object{
        const val FOLDER_PATH = "plugins/DVHsiege"
        const val DISTRESS_ZONE_ID = "#DISTRESS"
    }
    private var instance:DVHSiegeCore? = null
        get() {return field!!}

    override fun onEnable() {
        instance = this
        FileUtil.initFolder()
        Bukkit.getLogger().info("단츄 보라비 해야 화이팅!")

        val pm = Bukkit.getPluginManager()
        pm.registerEvents(PlayerMoveRegionListener(), this)
        pm.registerEvents(PlayerEnterRegionListener(), this)
        pm.registerEvents(PlayerLeaveRegionListener(), this)
    }
}
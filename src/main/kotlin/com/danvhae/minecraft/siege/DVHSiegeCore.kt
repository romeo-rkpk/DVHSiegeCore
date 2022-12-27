package com.danvhae.minecraft.siege

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class DVHSiegeCore : JavaPlugin() {
    private var instance:DVHSiegeCore? = null
        get() {return field!!}

    override fun onEnable() {
        instance = this
        Bukkit.getLogger().info("단츄 보라비 해야 화이팅!")
    }
}
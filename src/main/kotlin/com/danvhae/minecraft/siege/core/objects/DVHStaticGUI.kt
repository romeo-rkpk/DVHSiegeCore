package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.google.gson.Gson
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

class DVHStaticGUI(val title:String, val rows:Int, val buttons:List<DVHGUIButton>) {
    companion object{
        private const val FILE_NAME = "gui.json"
        private val GUIS = HashMap<String, DVHStaticGUI>()
        fun parseGUI(inventory: Inventory): DVHStaticGUI?{
            return GUIS[ChatColor.stripColor(inventory.title?:return null)]
        }

        fun load(){
            GUIS.clear()
            val json = FileUtil.readTextFile(FILE_NAME)?:return
            val arr = Gson().fromJson(json, Array<DVHStaticGUI>::class.java)?:return
            for(gui in arr){
                GUIS[ChatColor.stripColor(TextUtil.toColor(gui.title))] = gui
            }
        }

        operator fun get(id:String):DVHStaticGUI?{
            return GUIS[id]
        }
    }

    fun id():String{
        return ChatColor.stripColor(TextUtil.toColor(title))
    }

    fun createInventory():Inventory{
        val inventory = Bukkit.createInventory(null, 9 * rows, TextUtil.toColor(title))
        for(button  in buttons)
            inventory.setItem(button.slot, button.toItemStack())
        return inventory
    }
}
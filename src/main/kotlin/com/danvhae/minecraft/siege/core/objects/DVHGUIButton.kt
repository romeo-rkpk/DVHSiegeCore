package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class DVHGUIButton(val slot:Int, val name:String, val lore:Array<String>, type:Material, val data:Short = 0) {
    private val type:String = type.toString()
    fun toItemStack():ItemStack{
        val stack = ItemStack(Material.valueOf(type), 1, data)
        val meta = stack.itemMeta
        meta.displayName = TextUtil.toColor(name)
        val loreTemp = ArrayList<String>()
        for(line in lore)
            loreTemp.add(TextUtil.toColor(line))
        meta.lore = loreTemp
        stack.itemMeta = meta

        return stack
    }
}
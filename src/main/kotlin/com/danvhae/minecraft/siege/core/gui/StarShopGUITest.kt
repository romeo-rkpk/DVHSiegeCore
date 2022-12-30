package com.danvhae.minecraft.siege.core.gui

import com.danvhae.minecraft.siege.core.objects.DVHGUIButton
import com.danvhae.minecraft.siege.core.objects.DVHStaticGUI
import com.google.gson.GsonBuilder
import org.bukkit.Material
import org.junit.Test

class StarShopGUITest {
    companion object{
        private val gson = GsonBuilder().setPrettyPrinting().create()
    }
    @Test
    fun testMain(){
        val gui = DVHStaticGUI("&0은하", 6,  listOf(
            DVHGUIButton(11, "&c알데바란", arrayOf("&f방어에 특화되어 있는 강인한 별입니다"), Material.RECORD_4),
            DVHGUIButton(13, "&2레굴루스", arrayOf("&f농경에 특화된 웅장한 별입니다"),  Material.RECORD_10),
        ))

        val json = gson.toJson(gui)
        println(json)
        println()
        println()
        val obj = gson.fromJson(json, DVHStaticGUI::class.java)
        println(gson.toJson(obj))
    }

    @Test
    fun testConfirm(){
        val gui = DVHStaticGUI("&0성주 계약을 맺으시겠습니까?", 3, listOf(
            DVHGUIButton(10, "&c&l아니오", arrayOf(), Material.INK_SACK, 9),
        ))

        println(gson.toJson(gui))
    }

    @Test
    fun ampTest(){
        println("&")
        println("\u0026")
    }

}
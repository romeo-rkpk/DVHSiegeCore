package com.danvhae.minecraft.siege.core.utils

import org.junit.Test
import java.util.*
import kotlin.system.measureTimeMillis

class UUIDNameTest {

    @Test
    fun test1(){
        var uuid: UUID
        var name:String

        repeat(5) {
            for (testName in listOf("Romeo_RKPK", "DD_Haeya", "ninano0_0", "danchu_17")) {
                val time = measureTimeMillis {
                    uuid = NameUtil.nameToUUID(testName)!!
                    name = NameUtil.uuidToName(uuid)!!
                }


                println("$uuid $name (${time}ms)")
            }
        }

    }
}
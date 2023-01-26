package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.google.gson.Gson
import java.util.*

class SiegeOperator(val uuid: UUID, val name:String) {
    companion object{
       internal val DATA = HashMap<UUID, SiegeOperator>()
        operator fun contains(uuid:UUID):Boolean{
            return uuid in DATA.keys
        }

        private const val FILE_NAME = "operators.json"

        fun load(){
            DATA.clear()
            Gson().fromJson(FileUtil.readTextFile(FILE_NAME), Array<SiegeOperator>::class.java)!!.let { it ->
                it.forEach {
                    operator -> DATA[operator.uuid] = operator
                }
            }
        }
    }
}
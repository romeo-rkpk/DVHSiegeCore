package com.danvhae.minecraft.siege.core.utils

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import org.bukkit.Bukkit
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class FileUtil {
    companion object{

        private const val DEFAULT_FOLDER_NAME = "DVHSiege"
        fun initFolder(folderName:String = DEFAULT_FOLDER_NAME){
            val folder = File("plugins/${folderName}")
            if(!folder.exists())folder.mkdirs()
        }

        fun toBytes(obj:Any?):ByteArray?{
            obj?:return ByteArray(0)
            val baos = ByteArrayOutputStream()
            try{
                val boas = BukkitObjectOutputStream(baos)
                boas.writeObject(obj)
                boas.close()
            }catch (e:Exception){
                e.printStackTrace()
                return null
            }
            return baos.toByteArray()
        }

        fun fromBytes(bytes:ByteArray):Any?{
            //bytes
            val bais = ByteArrayInputStream(bytes)
            return try{
                val bois = BukkitObjectInputStream(bais)
                bois.readObject()
            }catch (exception:Exception){
                exception.printStackTrace()
                null
            }
        }

        fun writeBytes(bytes:ByteArray, fileName:String, logging:Boolean=false){
            try{
                if(logging) Bukkit.getLogger().info("${fileName}을(를) 저장하고 있습니다")
                Files.write(Paths.get(DVHSiegeCore.FOLDER_PATH + "/" + fileName), bytes)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        fun readBytes(fileName:String, logging: Boolean=false):ByteArray?{
            return try{
                if(logging)Bukkit.getLogger().info("${fileName}을(를) 읽고 있습니다")
                Files.readAllBytes(Paths.get(DVHSiegeCore.FOLDER_PATH + "/" + fileName))

            }catch (e:Exception){
                e.printStackTrace()
                null
            }
        }

        fun writeTextFile(text:String, fileName:String, folderName:String = DEFAULT_FOLDER_NAME){
            initFolder(folderName)
            try{
                val file = File("plugins/%s/%s".format(folderName, fileName))
                val stream = FileOutputStream(file)
                if(!file.exists())file.createNewFile()
                val bytes = text.toByteArray(StandardCharsets.UTF_8)
                stream.write(bytes)
                stream.close()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        fun readTextFile(fileName: String, folderName:String = DEFAULT_FOLDER_NAME):String?{
            return try{
                val file = File("plugins/%s/%s".format(folderName, fileName))
                val bytes = ByteArray(file.length().toInt())
                val stream = FileInputStream(file)
                stream.read(bytes)
                stream.close()
                String(bytes, StandardCharsets.UTF_8)
            }catch (e:Exception){
                e.printStackTrace()
                null
            }
        }
    }
}
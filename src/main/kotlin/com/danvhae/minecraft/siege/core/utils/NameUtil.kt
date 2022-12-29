package com.danvhae.minecraft.siege.core.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.bukkit.Bukkit
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class NameUtil {

    companion object{
        private val uuidToNameCache = HashMap<UUID, String>()
        private val nameToUUIDCache = HashMap<String, UUID>()

        private val nameUpperCaseCache = HashMap<String, String>()

        private fun httpRequest(urlString: String, parameter: String): String? {
            return try {
                val url = URL(urlString + parameter)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val reader = BufferedReader(
                    InputStreamReader(
                        connection.inputStream
                    )
                )
                var inputLine: String?
                val stringBuilder = StringBuilder()
                while (reader.readLine().also { inputLine = it } != null) {
                    stringBuilder.append(inputLine)
                }
                reader.close()
                stringBuilder.toString()
            } catch (e: IOException) {
                null
            }
        }

        private fun addDashToUUIDString(uuidString: String): String {
            return uuidString.replace(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(),
                "$1-$2-$3-$4-$5"
            ).replace("\"".toRegex(), "")
        }


        fun nameToUUID(_name: String?, checkOnline:Boolean = false): UUID? {
            _name?:return null

            val name = nameUpperCaseCache.getOrDefault(_name.uppercase(), _name)
            nameToUUIDCache[name]?.let { return it }

            if(checkOnline) {
                val player = Bukkit.getPlayer(name)
                player?.let {
                    nameToUUIDCache[player.name] = player.uniqueId
                    uuidToNameCache[player.uniqueId] = player.name
                    nameUpperCaseCache[player.name.uppercase()] = player.name
                    return player.uniqueId
                }
            }

            val json = httpRequest("https://api.mojang.com/users/profiles/minecraft/", name)
            val jsonObject = Gson().fromJson(json, JsonObject::class.java)
            jsonObject["id"]?.let {
                val uuid = UUID.fromString(addDashToUUIDString(it.toString()))
                val getName = jsonObject["name"]!!.asString!!
                uuidToNameCache[uuid] = getName
                nameToUUIDCache[getName] = uuid
                nameUpperCaseCache[getName.uppercase()] = getName
                return uuid
            }
            return null
            /*
            if (name == null) return null
            val player = Bukkit.getPlayer(name)
            if (player != null) return player.uniqueId

            var uuid: UUID? = nameToUUIDCache[name]
            if (uuid != null) return uuid
            val json = httpRequest("https://api.mojang.com/users/profiles/minecraft/", name)
            val `object` = Gson().fromJson(json, JsonObject::class.java)
            val element = `object`["id"] ?: return null
            val uuidString = element.toString() ?: return null
            val addHyphen = addDashToUUIDString(uuidString)
            uuid = UUID.fromString(addHyphen)
            val cachedName = Objects.requireNonNull(`object`["name"]).asString
            nameToUUIDCache.put(cachedName ?: name, uuid)
            uuidToNameCache.put(uuid, cachedName ?: name)
            return uuid

             */
        }

        fun uuidToName(uuid: UUID?, checkOnline: Boolean = false): String? {
            uuid?:return null
            uuidToNameCache[uuid]?.let { return it }
            /*
            if (uuid == null) return null

            val player = Bukkit.getPlayer(uuid)
            if (player != null) return player.name
            uuidToNameCache[uuid]?.let { return it }
            val json = httpRequest("https://sessionserver.mojang.com/session/minecraft/profile/", uuid.toString())
            val `object` = Gson().fromJson(json, JsonObject::class.java)
            val element = `object`["name"] ?: return null
            //result = element.asString
            //if (result == null) return null
            element.asString?.let {
                nameToUUIDCache[it] = uuid
                uuidToNameCache[uuid] = it
                return it
            }?:return null
             */
            if(checkOnline){
                Bukkit.getPlayer(uuid)?.let {
                    uuidToNameCache[uuid] = it.name
                    nameToUUIDCache[it.name] = uuid
                    return it.name
                }
            }

            val jsonObject = Gson().fromJson(
                httpRequest("https://sessionserver.mojang.com/session/minecraft/profile/", uuid.toString()),
                JsonObject::class.java
            )

            jsonObject["name"]?.let { it.asString?.let {jsonName->
                nameToUUIDCache[jsonName] = uuid
                uuidToNameCache[uuid] = jsonName
                nameUpperCaseCache[jsonName.uppercase()] = jsonName
                return jsonName

            } }
            return null
        }
    }
}
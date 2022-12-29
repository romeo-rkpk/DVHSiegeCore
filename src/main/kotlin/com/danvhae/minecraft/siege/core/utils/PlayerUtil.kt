package com.danvhae.minecraft.siege.core.utils

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class PlayerUtil {
    companion object{
        fun playerRegion(player:Player):HashSet<String>{
            val result = HashSet<String>()

            val manager = WorldGuardPlugin.inst().getRegionManager(player.world)
            val set = manager.getApplicableRegions(player.location)?:return result
            if(player.health <= 0)return result
            for(region in set.regions?:return result)
                result.add(region.id)
            if(isDistressZone(player.location))result.add(DVHSiegeCore.DISTRESS_ZONE_ID)
            return result
        }

        fun isDistressZone(location: Location):Boolean{
            if(location.world.name !in listOf("star", "DIM1"))return false
            //location이 worldguard 중 어느 하나 안에 있으면 성공
            for(castle in SiegeCastle.DATA.values){
                if(location.world != castle.attackPosition.world)continue
                val set = WorldGuardPlugin.inst().getRegionManager(location.world)
                    .getApplicableRegions(location)?:continue
                val idSet = HashSet<String>()
                for(region in set.regions ?:continue){
                    idSet.add(region.id)
                }

                if(castle.worldGuardID in idSet)return false
            }
            return true
        }

        private val uuidToNameCache = HashMap<UUID, String>()
        private val nameToUUIDCache = HashMap<String, UUID>()

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


        fun nameToUUID(name: String?): UUID? {
            name?:return null
            val player = Bukkit.getPlayer(name)
            player?.let {
                nameToUUIDCache[player.name] = player.uniqueId
                uuidToNameCache[player.uniqueId] = player.name
                return player.uniqueId
            }

            val json = httpRequest("https://api.mojang.com/users/profiles/minecraft/", name)
            val jsonObject = Gson().fromJson(json, JsonObject::class.java)
            jsonObject["id"]?.let {
                val uuid = UUID.fromString(addDashToUUIDString(it.toString()))
                val getName = jsonObject["name"]!!.asString!!
                uuidToNameCache[uuid] = getName
                nameToUUIDCache[getName] = uuid
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

        fun uuidToName(uuid: UUID?): String? {
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
        }
    }
}
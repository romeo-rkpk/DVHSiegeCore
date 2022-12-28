package com.danvhae.minecraft.siege.core.objects

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*

/**
 * 공성전에서의 한 팀을 의미합니다.
 * @property name 팀의 이름이자 ID입니다.
 * @property leaderUUID 팀의 우두머리 플레이어의 UUID입니다
 * @property remark 운영 측에서 남긴 메모입니다.
 */
class SiegeTeam(val name:String, val leaderUUID:UUID, val colorPrefix:String = "&f", var remark:String = ""){


    companion object{
        /**
         * 팀 데이터를 저장합니다.
         */
        val DATA = HashMap<String, SiegeTeam>()

        /**
         * 팀 데이터를 파일로 저장합니다
         */
        fun save(){
            DAO.save(DATA.values.toTypedArray())
        }

        /**
         * 파일로 저장된 팀 데이터를 불러옵니다
         */
        fun load(){
            DATA.clear()
            val array = DAO.load()
            for(team in array){
                DATA[team.name] = team
            }
        }
    }

    /**
     * 이 팀의 플레이어를 가져옵니다. 플레이어 추가 삭제는 SiegePlayer클래스 쪽에 물어보세요.
     */
    fun players():List<SiegePlayer>{
        val result = ArrayList<SiegePlayer>()
        for(player in SiegePlayer.DATA.values)
            if(player.team == name)
                result.add(player)
        
        return result.toList()
    }

    /**
     * 이 팀의 명의로 되어 있는 별을 모두 가져옵니다.
     */
    fun castles():List<SiegeCastle>{
        val castleIDs = SiegeCastle.DATA.keys
        val arrList = ArrayList<SiegeCastle>()
        for(castleID in castleIDs){
            val castle = SiegeCastle.DATA[castleID]?:continue
            if(castle.owner == leaderUUID)arrList.add(castle)
        }

        return arrList.toList()
    }

    /**
     * 이 팀의 명의로 되어 있는 별 중 게임상 유효한 것만을 가져옵니다
     */
    fun aliveCastle():List<SiegeCastle>{
        val temp = ArrayList<SiegeCastle>()
        val alls = castles()
        for(castle in alls){
            if(castle.status in listOf(
                    SiegeCastleStatus.PEACEFUL,
                    SiegeCastleStatus.UNDER_BATTLE))
                temp.add(castle)
        }
        return temp.toList()
    }

    private class DAO(val name:String, val leaderUUID:String, val colorPrefix: String, val remark: String){

        constructor(team: SiegeTeam):this(team.name, team.leaderUUID.toString(), team.colorPrefix, team.remark)

        companion object{
            private const val FILE_NAME = "teams.json"
            fun save(teams:Array<SiegeTeam>){
                val list = ArrayList<DAO>()
                for(team in teams)
                    list.add(DAO(team))
                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.toJson(list.toTypedArray())
                FileUtil.writeTextFile(json, FILE_NAME)
            }

            fun load():Array<SiegeTeam>{
                val gson = Gson()
                val json = FileUtil.readTextFile(FILE_NAME)
                val arr = gson.fromJson(json, Array<DAO>::class.java)

                val result = ArrayList<SiegeTeam>()
                for(e in arr)
                    result.add(SiegeTeam(e.name, UUID.fromString(e.leaderUUID), e.colorPrefix, e.remark))

                return result.toTypedArray()
            }
        }
    }
}
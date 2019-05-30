package webint.woodpecker.acquisition.twitter.streamproducer

import org.codehaus.jettison.json.JSONObject
import webint.woodpecker.common.database.mongo.model.Mission

object StringStatusMissionAdder {
    fun addMissionToStringStatus(stringStatus: String, mission: Mission): String{
        val jsonObj = JSONObject(stringStatus)
        jsonObj.put("mission_name",  mission.missionName)
        jsonObj.put("mission_group",  mission.missionGroup)
        return jsonObj.toString()
    }
}
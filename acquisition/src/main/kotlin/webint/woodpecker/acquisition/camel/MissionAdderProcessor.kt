package webint.woodpecker.acquisition.camel

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.codehaus.jettison.json.JSONObject
import org.springframework.stereotype.Component
import webint.woodpecker.common.database.mongo.model.Mission

@Component
class MissionAdderProcessor: Processor {

    var jsonStatus: JSONObject = JSONObject()

    override fun process(exchange: Exchange?) {
        val statusString: String? = exchange?.getIn()?.getBody(String::class.java)
        this.jsonStatus = JSONObject(statusString)
        val mission: Mission? = exchange?.getIn()?.getHeader("mission") as Mission?
        if (mission != null) {
            this.jsonStatus.put("mission_name", mission.missionName)
            this.jsonStatus.put("mission_group", mission.missionGroup)
        }
        exchange?.getIn()?.body = this.jsonStatus.toString()
    }

}
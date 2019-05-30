package webint.woodpecker.acquisition.camel

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.codehaus.jettison.json.JSONObject
import org.springframework.stereotype.Component
import webint.woodpecker.common.utils.log


@Component
class RetweetAndQuoteToIdProcessor : Processor {

    override fun process(exchange: Exchange?) {
        var statusString: String? = null
        try {
            statusString = exchange?.getIn()?.getBody(String::class.java)
            val jsonStatus = JSONObject(statusString)
            if (jsonStatus.has("quoted_status")) {
                jsonStatus.remove("quoted_status")
                //there are already "quoted_status_id" and "quoted_status_id_str" as independent fields
            }
            if (jsonStatus.has("retweeted_status")) {
                jsonStatus.put("retweeted_status_id", jsonStatus.getJSONObject("retweeted_status").getLong("id"))
                jsonStatus.put("retweeted_status_id_str", jsonStatus.getJSONObject("retweeted_status").getString("id_str"))
                jsonStatus.remove("retweeted_status")
            }
            exchange?.getIn()?.body = jsonStatus.toString()
        }catch(e: Exception){
            log().error("RetweetAndQuoteToIdProcessor error | statusString $statusString", e)
        }
    }

}
package webint.woodpecker.acquisition.camel

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.codehaus.jettison.json.JSONObject
import org.springframework.stereotype.Component
import webint.woodpecker.common.utils.log

@Component
class TweetQuoteProcessor : Processor {

    var jsonStatus: JSONObject = JSONObject()


    override fun process(exchange: Exchange?) {
        var statusString : String? = null
        try {
            statusString = exchange?.getIn()?.getBody(String::class.java)
            this.jsonStatus = JSONObject(statusString)

            if (jsonStatus.has("quoted_status")) {
                val quotedStatus = jsonStatus.get("quoted_status")
                //there are already "quoted_status_id" and "quoted_status_id_str" as independent fields
                exchange?.getIn()?.body = quotedStatus.toString()
            } else {
                exchange?.getIn()?.body = null
            }
        } catch (e: Exception) {
            log().error("TweetQuoteProcessor error | statusString:\n ${statusString}", e)
        }


    }

}
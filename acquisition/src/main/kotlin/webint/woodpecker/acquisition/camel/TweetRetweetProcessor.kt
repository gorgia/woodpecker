package webint.woodpecker.acquisition.camel

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.codehaus.jettison.json.JSONObject
import org.springframework.stereotype.Component
import webint.woodpecker.common.utils.log


@Component
class TweetRetweetProcessor : Processor {

    var jsonStatus: JSONObject = JSONObject()


    override fun process(exchange: Exchange?) {
        var statusString: String? = null
        try {
            statusString = exchange?.getIn()?.getBody(String::class.java)
            this.jsonStatus = JSONObject(statusString)
            if (jsonStatus.has("retweeted_status")) {
                val retweet = jsonStatus.get("retweeted_status")
                exchange?.getIn()?.body = retweet.toString()
            } else {
                exchange?.getIn()?.body = null
            }
        }catch(e: Exception){
            log().error("TweetRetweetProcessor error | statusString ${statusString}", e)
        }
    }

}
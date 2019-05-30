package webint.woodpecker.acquisition.twitter

import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import twitter4j.Status
import webint.woodpecker.common.utils.log
import javax.annotation.PostConstruct


@Component("twitterCamelKafkaConsumer")
@Lazy
class TwitterCamelKafkaConsumer {

    @PostConstruct
    fun init(){
        log().debug("I have been instantiated")
    }


    fun consume(status: Any){
        if(status is Status)
        log().info("Status received is:\n $status")
    }
}
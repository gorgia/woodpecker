package webint.woodpecker.common.kafka

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import webint.woodpecker.common.utils.log


@Component
open class KafkaStringSender{
    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, String>

    companion object {
        var twitterSuccessCount = 0
        var twitterFailureCount = 0
    }


    @Value( "\${spring.kafka.topic.event.tweets}")
    lateinit var tweetsTopic: String


    fun send(topic: String, payload: String): ListenableFuture<SendResult<String, String>> {
        val future = kafkaTemplate.send(topic, payload)

        future.addCallback(object : ListenableFutureCallback<SendResult<String, String>> {

            override fun onSuccess(result: SendResult<String, String>?) {
                if(topic == tweetsTopic)
                    log().debug("tweets sent successfully: ${++twitterSuccessCount} | $payload")
            }

            override fun onFailure(ex: Throwable) {
                if(topic == tweetsTopic)
                    log().debug("tweets sent unsuccessfully: ${++twitterFailureCount}:\n$ex")
            }
        })
        return future
    }
}
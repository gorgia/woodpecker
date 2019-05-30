package webint.woodpecker.common.kafka

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import webint.woodpecker.common.utils.log


@Component
open class KafkaJsonSender{
    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    fun send(topic: String, payload: Any): ListenableFuture<SendResult<String, Any>> {
        val future = kafkaTemplate.send(topic, payload)

        future.addCallback(object : ListenableFutureCallback<SendResult<String, Any>> {

            override fun onSuccess(result: SendResult<String, Any>?) {
                log().debug("Sent message=[" + payload +
                        "] with offset=[" + result!!.recordMetadata.offset() + "]")
            }

            override fun onFailure(ex: Throwable) {
                log().debug("Unable to send message=["
                        + payload + "] due to : " + ex.message)
            }
        })
        return future
    }
}
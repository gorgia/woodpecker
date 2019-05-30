package webint.woodpecker.webservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import twitter4j.Status
import twitter4j.TwitterObjectFactory
import webint.woodpecker.common.utils.log


@Controller
class WebSocketController {

    @Autowired
    lateinit var template: SimpMessagingTemplate

    companion object {
        var websocketCount = 0
    }

    @Value("\${spring.kafka.topic.event.tweets}")
    lateinit var tweetTopic: String

    @KafkaListener(topics = ["\${spring.kafka.topic.event.tweets}"], groupId = "websocket" )
    @MessageMapping("/websocket")
    fun sendMessage (statusString : String): Status {
        log().debug("WebSocket sent message n. ${++websocketCount}: $statusString")
        this.template.convertAndSend("/topic/tweets", statusString)
        return TwitterObjectFactory.createStatus(statusString)
    }
}
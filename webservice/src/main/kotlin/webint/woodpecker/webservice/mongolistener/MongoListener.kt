package webint.woodpecker.webservice.mongolistener

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent
import org.springframework.stereotype.Component
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.kafka.KafkaStringSender
import webint.woodpecker.common.utils.log


@Component
class MongoListener : AbstractMongoEventListener<TwitterMission>() {


    @Value( "\${spring.kafka.topic.event.twittermissionsave}")
    lateinit var topicToSendSaveEvent: String

    @Autowired
    lateinit var kafkaStringSender: KafkaStringSender

    @Autowired
    lateinit var objectMapper: ObjectMapper

//    override fun onBeforeConvert(event: BeforeConvertEvent<TwitterMission>) {
//        event.source.id = sequenceGenerator.generateSequence(MISSION_SEQUENCE_NAME)
//    }

    override fun onAfterSave(event: AfterSaveEvent<TwitterMission>) {
        log().debug("Mission save event detected: $event")
        try{
        val twitterMissionSource = objectMapper.writeValueAsString(event.source)
            kafkaStringSender.send(topicToSendSaveEvent, twitterMissionSource)
        }catch(e: Exception){
            log().error("onAfterSaveMission error", e)
        }

    }

    override fun onAfterDelete(event: AfterDeleteEvent<TwitterMission>) {
        log().debug("Mission delete event detected: $event")
        try{
            val twitterMissionSource = objectMapper.writeValueAsString(event.source)
            kafkaStringSender.send(topicToSendSaveEvent, twitterMissionSource)
        }catch(e: Exception){
            log().error("onAfterSaveMission error", e)
        }

    }
}
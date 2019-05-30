package webint.woodpecker.common.kafka.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.kafka.annotation.KafkaListener
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.utils.log

@ConditionalOnExpression("false")
abstract class KafkaEventTwitterMissionListener: KafkaEventListener<TwitterMission> {

    @Autowired
    //@Qualifier("OBJECT_MAPPER_BEAN")
    lateinit var objectMapper: ObjectMapper


    @Value("\${spring.kafka.topic.event.twittermissionsave}")
    lateinit var kafkaTopic: String



    @KafkaListener(topics = ["\${spring.kafka.topic.event.twittermissionsave}"], groupId =  "\${woodpecker.kafka.mongo.consumer.groupId}" )
    final override fun consumeSaveEvent(consumerRecord: ConsumerRecord<*, *>){
        log().debug("Catched event on kafkatopic: $kafkaTopic\n ${consumerRecord.value()}")
        this.actToSaveEvent(transformEventInObj(consumerRecord))
    }

    /*
    @ConditionalOnExpression("false")
    @KafkaListener(topics = ["\${spring.kafka.topic.event.twittermissiondelete}"], groupId =  "\${woodpecker.kafka.mongo.consumer.groupId}" )
    final override fun consumeDeleteEvent(consumerRecord: ConsumerRecord<*, *>){
        log().debug("Catched event on kafkatopic: $kafkaTopic\n ${consumerRecord.value()}")
        this.actToDeleteEvent(transformEventInObj(consumerRecord))
    }
    */



    final override fun transformEventInObj(consumerRecord: ConsumerRecord<*, *>): TwitterMission {
        val value: String = consumerRecord.value().toString()
        return objectMapper.readValue(value)
    }

}
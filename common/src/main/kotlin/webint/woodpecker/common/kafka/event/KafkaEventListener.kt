package webint.woodpecker.common.kafka.event

import org.apache.kafka.clients.consumer.ConsumerRecord


interface KafkaEventListener<T> {
    fun consumeSaveEvent(consumerRecord: ConsumerRecord<*, *>) {
        actToSaveEvent(transformEventInObj(consumerRecord))
    }

    fun consumeDeleteEvent(consumerRecord: ConsumerRecord<*, *>) {
        actToDeleteEvent(transformEventInObj(consumerRecord))
    }

    fun transformEventInObj(consumerRecord: ConsumerRecord<*, *>): T


    fun actToSaveEvent(eventObj: T)

    fun actToDeleteEvent(eventObj: T)
}
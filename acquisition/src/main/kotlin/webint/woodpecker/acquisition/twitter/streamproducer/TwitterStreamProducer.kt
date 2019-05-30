package webint.woodpecker.acquisition.twitter.streamproducer

import org.apache.camel.ProducerTemplate
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import twitter4j.TwitterStream
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.utils.log


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class TwitterStreamProducer(twitterMission: TwitterMission, twitterStream: TwitterStream) : AbstractTwitterStreamProducer(twitterMission, twitterStream), InitializingBean {


    private var twitterCount = 0


    @Value("\${spring.kafka.topic.event.tweets}")
    lateinit var tweetsTopic: String

    @Autowired
    lateinit var producerTemplate: ProducerTemplate

    @Value("\${tweet.camel.processPipeline.start}")
    lateinit var tweetProcessingPipelineStart: String




    override fun afterPropertiesSet() {
       // if (autoStart)
       //     this.isActive = twitterMission.startDateTime.isBefore(LocalDateTime.now()) && twitterMission.endDateTime.isAfter(LocalDateTime.now())
    }


    override fun myOnStatus(toStringedStatus: String?): String? {
        log().debug("tweets received ${++twitterCount} from TwitterStreamProducer${System.identityHashCode(this)} | last tweet: $toStringedStatus")
        producerTemplate.sendBodyAndHeader(tweetProcessingPipelineStart, toStringedStatus, "mission", this.twitterMission)
        return toStringedStatus
    }


}
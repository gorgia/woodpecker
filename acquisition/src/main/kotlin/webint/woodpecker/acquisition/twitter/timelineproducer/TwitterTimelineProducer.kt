package webint.woodpecker.acquisition.twitter.timelineproducer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.apache.camel.ProducerTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import twitter4j.*
import webint.woodpecker.acquisition.scheduler.TwitterTask
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.utils.log
import java.util.*




@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class TwitterTimelineProducer(twitterMission: TwitterMission, val twitter: Twitter) : TwitterTask(twitterMission){

    @Value("\${tweet.camel.processPipeline.start}")
    lateinit var tweetProcessingPipelineStart: String

    @Autowired
    lateinit var producerTemplate: ProducerTemplate

    var job: Job = Job()

    override fun stop() {
        this.job.cancel()
    }

    override fun run() {
        this.job = GlobalScope.launch{
            log().debug("Start grabbing timeline of:${twitterMission.query}")
            val statuses = ArrayList<Status>()
            var pageno = 1
            var size = 0
            do{
                try {
                    log().debug("getting tweets")
                    size = statuses.size // actual tweets count we got
                    val page = Paging(pageno, 200)
                    val cycleStatuses = twitter.getUserTimeline(twitterMission.query, page)
                    camelSend(cycleStatuses)
                    statuses.addAll(cycleStatuses)
                    log().debug("total got : " + statuses.size)
                    pageno++
                    delay(1000) // 900 rqt / 15 mn <=> 1 rqt/s
                } catch (e: TwitterException) {
                    log().error(e.errorMessage)
                }
            }while(statuses.size!=size)
            log().debug("Finished grabbing timeline of: ${twitterMission.query}. Total tweets = ${statuses.size}")
        }
    }




    fun camelSend(statuses: List<Status>){
        GlobalScope.launch {
            statuses.forEach {
                val stringedStatus = TwitterObjectFactory.getRawJSON(it)
                producerTemplate.sendBodyAndHeader(tweetProcessingPipelineStart, stringedStatus, "mission", twitterMission)
                }
        }
    }


}
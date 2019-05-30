package webint.woodpecker.twitter.timelineproducer

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner
import twitter4j.Twitter
import webint.woodpecker.acquisition.twitter.timelineproducer.TwitterTimelineProducer
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.database.mongo.model.TwitterMissionType
import webint.woodpecker.common.springconfig.TestApplicationConfig

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [TestApplicationConfig::class])
//@SpringBootTest(classes = [AcquisitionApplication::class])
internal open class TwitterTimelineProducerTest {

    @Autowired
    lateinit var ctx: ApplicationContext

    private var twitterTimelineProducer: TwitterTimelineProducer? = null

    @Before
    fun setUp() {
        val twitterMission = TwitterMission("FT", type = TwitterMissionType.TIMELINE)
        val twitter : Twitter = ctx.getBean()
        this.twitterTimelineProducer = ctx.getBean(TwitterTimelineProducer::class.java, twitterMission, twitter)
    }

    @Test
    fun run() {
        twitterTimelineProducer?.isRunning = true
        Thread.sleep(3000)
    }
}
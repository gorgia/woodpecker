package webint.woodpecker.acquisition.scheduler

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import twitter4j.Twitter
import twitter4j.TwitterStream
import webint.woodpecker.acquisition.twitter.streamproducer.TwitterStreamProducer
import webint.woodpecker.acquisition.twitter.timelineproducer.TwitterTimelineProducer
import webint.woodpecker.common.database.mongo.model.TwitterCredentials
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.database.mongo.model.TwitterMissionType
import webint.woodpecker.common.database.mongo.repository.TwitterMissionRepository
import webint.woodpecker.common.kafka.event.KafkaEventTwitterMissionListener

@Component
class TwitterScheduler : KafkaEventTwitterMissionListener() {

    @Autowired
    lateinit var twitterMissionRepository: TwitterMissionRepository

    @Autowired
    lateinit var ctx: ApplicationContext

    private var twitterMissionList: MutableList<TwitterMission>? = null

    private val taskList = LinkedHashMap<ObjectId, TwitterTask>()



    fun start() {
        twitterMissionList = twitterMissionRepository.findAll()
        twitterMissionList?.forEach {
            if (!taskList.containsKey(it.id))
                taskList[it.id] = createTaskFromMission(it)
        }
        activateAllTasks()
    }

    fun createTaskFromMission(twitterMission: TwitterMission): TwitterTask {
        val twitterTask: TwitterTask
        val twitterCredentials = ctx.getBean(TwitterCredentials::class.java, twitterMission.credentialsId ?: "default")
        twitterTask = when (twitterMission.type) {
            TwitterMissionType.STREAM -> {
                val twitterStream: TwitterStream = ctx.getBean(twitterCredentials)
                ctx.getBean(twitterMission, twitterStream) as TwitterStreamProducer
            }
            TwitterMissionType.TIMELINE -> {
                val twitter: Twitter = ctx.getBean(twitterCredentials)
                ctx.getBean(twitterMission, twitter) as TwitterTimelineProducer
            }
        }
        return twitterTask
    }


    fun activateAllTasks() {
        taskList.values.forEach { runOrStopTask(it) }
    }

    fun runOrStopTask(twitterTask: TwitterTask) {
        twitterTask.isRunning = twitterTask.twitterMission.isActive && twitterTask.checkDates()
    }

    override fun actToSaveEvent(eventObj: TwitterMission) {
        this.taskList[eventObj.id]?.isRunning = false
        val twitterTask = createTaskFromMission(eventObj)
        this.taskList[eventObj.id] = twitterTask
        runOrStopTask(twitterTask)
    }

    override fun actToDeleteEvent(eventObj: TwitterMission) {
        this.taskList[eventObj.id]?.isRunning = false
    }


}
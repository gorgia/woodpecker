package webint.woodpecker.acquisition.twitter.streamproducer

import twitter4j.*
import webint.woodpecker.acquisition.scheduler.TwitterTask
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.utils.log

abstract class AbstractTwitterStreamProducer(twitterMission: TwitterMission, private val twitterStream: TwitterStream) : TwitterTask(twitterMission) {

    private fun getStatusListener(): StatusListener {
        return object : StatusListener {
            override fun onTrackLimitationNotice(p0: Int) {
                log().error("Track limitation notice: $p0")
            }

            override fun onStallWarning(p0: StallWarning?) {
                log().error("onStallWarning: $p0")
            }

            override fun onException(p0: Exception?) {
                log().error("TwitterStream exception", p0)
            }

            override fun onDeletionNotice(p0: StatusDeletionNotice?) {
                log().warn("Deletion Notice", p0)
            }

            override fun onScrubGeo(p0: Long, p1: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStatus(p0: Status?) {
                myOnStatus(TwitterObjectFactory.getRawJSON(p0))
            }
        }
    }

    abstract fun myOnStatus(toStringedStatus: String?): String?


    override fun run() {
        log().info("Starting twitter stream from mission ${twitterMission.missionName}. Query: ${twitterMission.query}")
        twitterStream.addListener(getStatusListener())
        val filterQuery = FilterQuery(*trimmedStringArray(twitterMission.query))
        twitterStream.filter(filterQuery)
    }

    private fun trimmedStringArray(query: String): Array<String> {
        val trimmedList = ArrayList<String>()
        query.split(",").forEach { trimmedList.add(it.trim()) }
        return trimmedList.toTypedArray()
    }

    override fun stop() {
        log().info("Stopping twitter stream from mission ${twitterMission.missionName}. Query: ${twitterMission.query}")
        try {
            twitterStream.cleanUp()
        } catch (e: Exception) {
            log().error("error during stop of twitterStream: ${twitterMission.missionName}", e)
        }
    }

}
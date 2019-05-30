package webint.woodpecker.acquisition.scheduler

import webint.woodpecker.common.database.mongo.model.TwitterMission
import java.time.LocalDateTime

abstract class TwitterTask(val twitterMission: TwitterMission) : Task() {
        override var startDateTime: LocalDateTime = twitterMission.startDateTime

    override var endDateTime: LocalDateTime = twitterMission.endDateTime


}
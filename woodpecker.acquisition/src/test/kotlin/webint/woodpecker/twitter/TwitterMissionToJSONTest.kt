package webint.woodpecker.twitter

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import twitter4j.TwitterStream
import webint.woodpecker.acquisition.AcquisitionApplication
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.utils.log


@SpringBootTest(classes = [AcquisitionApplication::class])
@ExtendWith(SpringExtension::class)
class TwitterMissionToJSONTest (@Autowired private val twitterStream: TwitterStream) {

    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    fun convert() {
        val query = "prova"
        val twitterMission = TwitterMission(query)
        twitterMission.missionName = "provamissionname"
        log().info("twitterMissionJson : ${mapper.writeValueAsString(twitterMission)}")
    }
}
package webint.woodpecker.webservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.database.mongo.repository.TwitterMissionRepository
import webint.woodpecker.common.utils.log
import javax.validation.Valid

@RestController
@CrossOrigin(origins =["*"], allowedHeaders = ["*"])
@RequestMapping("/api")
class MissionController {

    @Autowired
    lateinit var twitterMissionRepo: TwitterMissionRepository

    @GetMapping(path = ["/mission/twitter/_all"], produces = ["application/json"])
    @ResponseBody
    fun getAllTwitterMissions(): List<TwitterMission> {
        log().debug("returning all saved missions")
        val missionList = twitterMissionRepo.findAll()
        return missionList
    }


    @RequestMapping(path = ["/mission/twitter"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    fun addNewMission(@Valid @RequestBody twitterMission: TwitterMission): TwitterMission {
        val savedTwitterMission = twitterMissionRepo.save(twitterMission)
        log().info("$savedTwitterMission successfully saved into DB\n$savedTwitterMission")
        return savedTwitterMission
    }

    @RequestMapping(path = ["/mission/twitter"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteMission(@Valid @RequestBody twitterMission: TwitterMission){
        twitterMissionRepo.deleteById(twitterMission.id)
        log().info("deleted Twitter Mission by Id:${twitterMission.id}")
    }

}
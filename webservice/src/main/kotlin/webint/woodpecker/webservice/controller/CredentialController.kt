package webint.woodpecker.webservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import webint.woodpecker.common.database.mongo.model.TwitterCredentials
import webint.woodpecker.common.database.mongo.repository.TwitterCredentialsRepository
import webint.woodpecker.common.utils.log
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/api")
class CredentialController {

    @Autowired
    lateinit var twitterCredRepo: TwitterCredentialsRepository

    @GetMapping(path = ["/credentials/twitter/_all"])
    @ResponseBody
    fun getAllTwitterMissions(): List<TwitterCredentials> {
        log().debug("returning all saved credentials")
        val credentialsList = twitterCredRepo.findAll()
        return credentialsList
    }


    @RequestMapping(path = ["/credentials/twitter"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    fun addNewMission(@Valid @RequestBody twitterCredentials: TwitterCredentials): TwitterCredentials {
        val savedTwitterCredentials = twitterCredRepo.save(twitterCredentials)
        log().info("$savedTwitterCredentials successfully saved into DB\n$savedTwitterCredentials")
        return savedTwitterCredentials
    }

    @RequestMapping(path = ["/credentials/twitter"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteMission(@Valid @RequestBody twitterCredentials: TwitterCredentials){
        twitterCredRepo.deleteById(twitterCredentials.name)
        log().info("deleted Twitter Mission by Id:${twitterCredentials.name}")
    }

}
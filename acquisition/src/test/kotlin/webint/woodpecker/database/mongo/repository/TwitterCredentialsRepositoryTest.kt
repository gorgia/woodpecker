package webint.woodpecker.database.mongo.repository

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import webint.woodpecker.acquisition.AcquisitionApplication
import webint.woodpecker.common.database.mongo.model.TwitterCredentials
import webint.woodpecker.common.database.mongo.repository.TwitterCredentialsRepository
import kotlin.test.assertNotNull


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AcquisitionApplication::class])
@EnableConfigurationProperties
open class TwitterCredentialsRepositoryTest {

    companion object {
        const val defaultConsumerKey = "AbnXYnwsJgAnazpYg2op1w"
        const val defaultConsumerSecret = "98eG46AgSNq7HF4nL4DCkyXaCfAe86iVnGCgqxrwM"
        const val defaultAccessToken = "543279710-zIpG4wcY6TTdQCiy5eHo7ZjJc7a9f2oS7bsGEpo3"
        const val defaultAccessTokenSecret= "knIg4Z3dhgDZQ08yUy4p5a0hTA09duCohigNy529Iw"
    }


    @Autowired
    lateinit var twitterCredentialsRepository: TwitterCredentialsRepository

    //@Test
    open fun save() {
        val twitterCredentials = TwitterCredentials(name = "default", consumerKey = defaultConsumerKey, consumerSecret = defaultConsumerSecret, accessToken = defaultAccessToken, accessTokenSecret = defaultAccessTokenSecret)
        assertNotNull(twitterCredentialsRepository.save(twitterCredentials))
    }

    @Test
    open fun getCredentialsFromDB () {
        val defaultCred = twitterCredentialsRepository.findByName("default")
        assertNotNull(defaultCred)
    }

}
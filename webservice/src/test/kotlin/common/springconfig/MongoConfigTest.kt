package common.springconfig

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import webint.woodpecker.common.springconfig.TestApplicationConfig


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [TestApplicationConfig::class])
class MongoConfigTest{

    @Autowired
    lateinit var twitterCredRepo: webint.woodpecker.common.database.mongo.repository.TwitterCredentialsRepository

    @Test
    fun test(){
        Assert.assertNotNull(twitterCredRepo)
    }

}
package webint.woodpecker.twitter

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import twitter4j.FilterQuery
import webint.woodpecker.acquisition.AcquisitionApplication
import webint.woodpecker.acquisition.twitter.streamproducer.TwitterStreamProducer
import webint.woodpecker.common.utils.ReadFileFromResources
import webint.woodpecker.common.utils.log
import java.util.concurrent.Executors


@SpringBootTest(classes = [AcquisitionApplication::class])
@ExtendWith(SpringExtension::class)
class TwitterCamelKafkaConsumerTest {

    @Autowired
    lateinit var ctx: ApplicationContext


    @Test
    fun retrieveStream() {
        log().debug("LOGGING LEVEL DEBUG ENABLED")
        val filterQuery = FilterQuery()
        filterQuery.track(*ReadFileFromResources.readToList("testfile.txt").toTypedArray())
        val twitterStreamProducer: TwitterStreamProducer = ctx.getBean(filterQuery)
        val singleThreadDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        var resultThatWillNeverCome : Any? = null
        runBlocking {  resultThatWillNeverCome = withContext(singleThreadDispatcher) { coroutineScope(twitterStreamProducer) } }
        log().info(resultThatWillNeverCome?.toString())

    }

    private suspend fun coroutineScope(twitterStreamProducer: TwitterStreamProducer) {
        twitterStreamProducer.isRunning = true
    }

}
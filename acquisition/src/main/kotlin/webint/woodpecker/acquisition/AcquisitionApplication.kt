package webint.woodpecker.acquisition

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.Scheduled
import webint.woodpecker.acquisition.scheduler.TwitterScheduler
import webint.woodpecker.common.springconfig.KafkaConfig
import webint.woodpecker.common.springconfig.MongoConfig
import webint.woodpecker.common.utils.log

@Import(MongoConfig::class, KafkaConfig::class)
@SpringBootApplication
open class AcquisitionApplication: InitializingBean {

    @Autowired
    lateinit var twitterScheduler: TwitterScheduler

    override fun afterPropertiesSet() {
        twitterScheduler.start()
    }

    //Executes each day at 24
    @Scheduled(cron  = "0 0 0 * * *")
    fun checkHealt() {
        log().info("${this.javaClass} is fine. Proper healt check to be implemented")
    }
}


fun main(args: Array<String>) {
    log(String::class).info("WOODPECKER STARTED")
    runApplication<AcquisitionApplication>(*args)
}



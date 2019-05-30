package webint.woodpecker.webservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import webint.woodpecker.common.springconfig.MongoConfig


@SpringBootApplication
@Import(MongoConfig::class)
class WebserviceApplication {

}

fun main(args: Array<String>) {
    runApplication<WebserviceApplication>(*args)
}
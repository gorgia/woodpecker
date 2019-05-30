package webint.woodpecker.common.springconfig

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@ComponentScan(basePackages = ["webint.woodpecker.common.database"] )
@EnableJpaRepositories("webint.woodpecker.common.database")
@EnableMongoRepositories("webint.woodpecker.common.database")
@EnableAutoConfiguration
class TestApplicationConfig {

}

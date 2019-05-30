package webint.woodpecker.common.springconfig

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories




@Configuration
@EnableJpaRepositories(basePackages =["webint.woodpecker"])
@EntityScan(basePackages =["webint.woodpecker"])
@ComponentScan(basePackages =["webint.woodpecker"])
class MongoConfig {

    @Bean
    fun jsonObjectMapper(): ObjectMapper {
        val mapper =  ObjectMapper().registerModules(KotlinModule(), Jdk8Module(), JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return mapper
    }

}

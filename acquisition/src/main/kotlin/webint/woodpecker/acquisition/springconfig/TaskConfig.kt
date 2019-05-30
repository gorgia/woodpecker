package webint.woodpecker.acquisition.springconfig

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor


@Configuration
open class TaskConfig {
    @Bean("threadPool")
    open fun threadPoolTaskExecutor(): ThreadPoolTaskExecutor {
        val threadPoolTaskExecutor = ThreadPoolTaskExecutor()
        threadPoolTaskExecutor.corePoolSize = 5
        threadPoolTaskExecutor.maxPoolSize = 5
        threadPoolTaskExecutor.threadNamePrefix = "TwitterStream"
        threadPoolTaskExecutor.initialize()
        return threadPoolTaskExecutor
    }
}
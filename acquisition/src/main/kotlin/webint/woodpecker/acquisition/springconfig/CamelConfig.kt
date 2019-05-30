package webint.woodpecker.acquisition.springconfig

import org.apache.camel.CamelContext
import org.apache.camel.ProducerTemplate
import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import webint.woodpecker.acquisition.camel.MissionAdderProcessor
import webint.woodpecker.acquisition.camel.RetweetAndQuoteToIdProcessor
import webint.woodpecker.acquisition.camel.TweetQuoteProcessor
import webint.woodpecker.acquisition.camel.TweetRetweetProcessor


@Configuration
open class CamelConfig {

    @Autowired
    lateinit var camelContext: CamelContext

    @Autowired
    lateinit var ctx: ApplicationContext

    @Bean
    open fun producertTemplate(): ProducerTemplate{
        return camelContext.createProducerTemplate()
    }

    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var kafkaBrokerAddress: String

    @Value("\${tweet.camel.processPipeline.start}") lateinit var tweetProcessingPipelineStart: String


    @Bean
    open fun myRouter(): RouteBuilder {
        return object : RouteBuilder() {
            @Throws(Exception::class)
            override fun configure() {

                interceptFrom("direct:intercept*").`when`(body().isNull()).stop()

                from(tweetProcessingPipelineStart).multicast().parallelProcessing().to("direct:TweetRetweetProcessor", "direct:TweetQuoteProcessor", "direct:TweetRetweetAndQuoteToIdProcessor")

                from("direct:TweetRetweetProcessor").process(ctx.getBean(TweetRetweetProcessor::class.java)).to("direct:interceptTweetRetweetProcessor")
                        from("direct:interceptTweetRetweetProcessor").process(ctx.getBean(MissionAdderProcessor::class.java)).to("kafka:topic1?brokers=$kafkaBrokerAddress")
                from("direct:TweetQuoteProcessor").process(ctx.getBean(TweetQuoteProcessor::class.java)).to("direct:interceptTweetQuoteProcessor")
                        from("direct:interceptTweetQuoteProcessor").process(ctx.getBean(MissionAdderProcessor::class.java)).to("kafka:topic1?brokers=$kafkaBrokerAddress")
                from("direct:TweetRetweetAndQuoteToIdProcessor").process(ctx.getBean(RetweetAndQuoteToIdProcessor::class.java)).process(ctx.getBean(MissionAdderProcessor::class.java)).to("kafka:topic1?brokers=$kafkaBrokerAddress")



                //from("kafka:topic1?brokers=$kafkaBrokerAddress&groupId=socket").bean(WebSocketController::class.java, "sendMessage")


            }
        }
    }

}
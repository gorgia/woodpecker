package webint.woodpecker.acquisition.springconfig

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import twitter4j.FilterQuery
import twitter4j.Twitter
import twitter4j.TwitterStream
import twitter4j.conf.ConfigurationBuilder
import webint.woodpecker.common.database.mongo.model.TwitterCredentials
import webint.woodpecker.common.database.mongo.model.TwitterMission
import webint.woodpecker.common.database.mongo.repository.TwitterCredentialsRepository


@Configuration
open class TwitterConfig {

    @Autowired
    lateinit var twitterCredentialsRepository: TwitterCredentialsRepository

    @Bean
    open fun getFilterQuery(): FilterQuery {
        return FilterQuery()
    }

    @Bean
    open fun getTwitterMission(): TwitterMission {
        return TwitterMission("prova, default")
    }


    @Bean
    @Scope(value = "prototype")
    open fun getTwitterCredentials(@Value ("\${tweet.credentials.default}")credentialsName : String): TwitterCredentials {
        val defaultConsumerKey = "AbnXYnwsJgAnazpYg2op1w"
        val defaultConsumerSecret = "98eG46AgSNq7HF4nL4DCkyXaCfAe86iVnGCgqxrwM"
        val defaultAccessToken = "543279710-zIpG4wcY6TTdQCiy5eHo7ZjJc7a9f2oS7bsGEpo3"
        val defaultAccessTokenSecret= "knIg4Z3dhgDZQ08yUy4p5a0hTA09duCohigNy529Iw"
        return twitterCredentialsRepository.findByName(credentialsName)?: TwitterCredentials(name = "default", consumerKey = defaultConsumerKey,
                consumerSecret = defaultConsumerSecret, accessToken = defaultAccessToken, accessTokenSecret = defaultAccessTokenSecret, proxyHost = "192.168.222.101", proxyPort = 3001)
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    open fun getTwitter(twitterCredentials : TwitterCredentials): Twitter{
        val cb = getConfigurationBuilder(twitterCredentials)
        val tf: twitter4j.TwitterFactory = twitter4j.TwitterFactory(cb.build())
        return  tf.instance
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    open fun getTwitterStream(twitterCredentials: TwitterCredentials): TwitterStream {
        val cb = getConfigurationBuilder(twitterCredentials)
        val tf: twitter4j.TwitterStreamFactory = twitter4j.TwitterStreamFactory(cb.build())
        return  tf.instance
    }

    open fun getConfigurationBuilder(twitterCredentials: TwitterCredentials): ConfigurationBuilder{
        val cb = ConfigurationBuilder()
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterCredentials.consumerKey)
                .setOAuthConsumerSecret(twitterCredentials.consumerSecret)
                .setOAuthAccessToken(twitterCredentials.accessToken)
                .setOAuthAccessTokenSecret(twitterCredentials.accessTokenSecret)
                .setJSONStoreEnabled(true)
                .setTweetModeExtended(true)
        if(twitterCredentials.proxyHost != null && twitterCredentials.proxyPort != null){ //setProxy if needed
            cb.setHttpProxyHost(twitterCredentials.proxyHost)
            cb.setHttpProxyPort(twitterCredentials.proxyPort!!)
        }
        return cb
    }



}

package webint.woodpecker.common.database.mongo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "twittercredentials")
data class TwitterCredentials(@Id val name: String, val consumerKey: String, val consumerSecret: String, val accessToken: String, val accessTokenSecret: String, val proxyHost: String? = null, val proxyPort: Int? = null) {
}
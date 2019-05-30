package webint.woodpecker.common.database.mongo.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import webint.woodpecker.common.database.mongo.model.TwitterCredentials


@Repository
interface TwitterCredentialsRepository: MongoRepository<TwitterCredentials, String> {
    fun findByName(credentialsName: String): TwitterCredentials?
}
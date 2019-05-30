package webint.woodpecker.common.database.mongo.repository

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import webint.woodpecker.common.database.mongo.model.TwitterMission


@Repository
interface TwitterMissionRepository: MongoRepository<TwitterMission, ObjectId> {
    fun findByMissionName(missionName: String): TwitterMission
}
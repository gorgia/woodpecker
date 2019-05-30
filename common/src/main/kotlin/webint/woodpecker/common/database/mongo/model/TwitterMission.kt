package webint.woodpecker.common.database.mongo.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank


@Document(collection = "twittermission")
data class TwitterMission(@NotBlank var query: String, val type: TwitterMissionType = TwitterMissionType.STREAM) : Mission {

    @Id
    @JsonSerialize(using = ToStringSerializer::class)
    var id: ObjectId = ObjectId.get()

    override var missionName: String = ""
    override var missionGroup: String = ""

    var index: String = "twitter"

    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="uuuu-MM-dd'T'HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
//    @JsonSerialize(using = LocalDateTimeSerializer::class)
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
    var creationDateTime: LocalDateTime = LocalDateTime.now()

    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="uuuu-MM-dd'T'HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer::class) //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="YYYY-MM-DD'T'HH:mm:ss")
//    @JsonSerialize(using = LocalDateTimeSerializer::class)
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
    var startDateTime: LocalDateTime = LocalDateTime.now()

    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="uuuu-MM-dd'T'HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
//    @JsonSerialize(using = LocalDateTimeSerializer::class)
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
    var endDateTime: LocalDateTime = LocalDateTime.now().plusDays(10)

    var frequencySeconds: Int = 60
    @JsonProperty("isActive")
    var isActive: Boolean = false
    var credentialsId: String? = null
}
package webint.woodpecker.webservice.controller

/*
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner
import webint.SpringBootVuejsApplication
import webint.woodpecker.database.mongo.model.TwitterMission
import User

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [SpringBootVuejsApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendControllerTest {

    @Autowired
    var backendController: BackendController? = null

    @LocalServerPort
    private val port: Int = 0

    @Before
    fun init() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
    }

    @Test
    fun addNewUserAndRetrieveItBack() {
        val userId = given()
                .queryParam("firstName", "Norbert")
                .queryParam("lastName", "Siegmund")
                .`when`()
                .post("/api/user")
                .then()
                .statusCode(`is`(HttpStatus.SC_CREATED))
                .extract()
                .body().`as`(Long::class.java)

        val responseUser = given()
                .pathParam("id", userId)
                .`when`()
                .get("/api/user/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .extract().`as`(User::class.java)

        // Did Norbert came back?
        assertThat(responseUser.firstName, `is`("Norbert"))
        assertThat(responseUser.lastName, `is`("Siegmund"))
    }

    @Test
    fun addNewMissionAndRetrieveItBack() {
        val twitterMission = TwitterMission("provaccia")
        twitterMission.missionName = "provamissionname"

        val userId = given()
                .queryParam("firstName", "Norbert")
                .queryParam("lastName", "Siegmund")
                .`when`()
                .post("/api/user")
                .then()
                .statusCode(`is`(HttpStatus.SC_CREATED))
                .extract()
                .body().`as`(Long::class.java)

        val responseUser = given()
                .pathParam("id", userId)
                .`when`()
                .get("/api/user/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .extract().`as`(User::class.java)

        // Did Norbert came back?
        assertThat(responseUser.firstName, `is`("Norbert"))
        assertThat(responseUser.lastName, `is`("Siegmund"))
    }

}
*/
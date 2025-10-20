package tests.tasks;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetBookingTest {

    @Test
    public void verifyBookingDetails() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/booking/32")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstname", equalTo("Josh"))
                .body("lastname", equalTo("Allen"))
                .body("totalprice", equalTo(111));
    }
}
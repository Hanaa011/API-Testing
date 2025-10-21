package tests.homeworks;

import base_urls.FakerRestApiBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.ActivityPojo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FakerRestApiCRUDTest extends FakerRestApiBaseUrl {

    int activityId;

    // ---------- CREATE (POST) ----------
    @Test(priority = 1)
    public void createActivity() {
        // Create the payload (request body)
        ActivityPojo payload = new ActivityPojo(0, "Coding Practice", "2025-10-21T12:00:00", true);
        // Send POST request to create a new activity
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/Activities");
        // Verify response status and content
        response.then()
                .statusCode(200)
                .body("title", equalTo("Coding Practice"));

        // Store the generated ID
        activityId = response.jsonPath().getInt("id");
        System.out.println("Created ID = " + activityId);
    }
    // ---------- READ (GET) ----------
    @Test(priority = 2)
    public void getActivity() {
        // Send GET request to retrieve the created activity
        Response response = given(spec)
                .when()
                .get("/Activities/" + activityId);

        // Validate the response
        response.then()
                .statusCode(200)
                .body("id", equalTo(activityId))
                .body("title", equalTo("Coding Practice"));
    }

    // ---------- UPDATE (PUT) ----------
    @Test(priority = 3)
    public void updateActivity() {
        // Create updated data for the existing activity
        ActivityPojo updated = new ActivityPojo(activityId, "Updated Task", "2025-10-25T14:00:00", false);
        // Send PUT request to update the activity
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(updated)
                .when()
                .put("/Activities/" + activityId);

        // Verify the update result
        response.then()
                .statusCode(200)
                .body("title", equalTo("Updated Task"))
                .body("completed", equalTo(false));
    }
    // ---------- DELETE ----------
    @Test(priority = 4)
    public void deleteActivity() {
        // Send DELETE request to remove the activity
        Response response = given(spec)
                .when()
                .delete("/Activities/" + activityId);

        // Check if delete was successful
        response.then()
                .statusCode(200);

        // Try to GET the deleted activity to confirm it no longer exists
        given(spec)
                .when()
                .get("/Activities/" + activityId)
                .then()
                .statusCode(404);
    }
}

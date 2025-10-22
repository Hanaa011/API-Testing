package tests.homeworks;

import base_urls.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import util.TestUtils;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateCartTest extends BaseTest {

    @Test
    public void createCartUsingJsonFile() throws Exception {

        // Step 1: Load the JSON file that contains the cart data
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("src/test/resources/tests_data/cartData.json");
        JsonNode cartData = mapper.readTree(jsonFile);

        // Step 2: Convert the JSON to ObjectNode to modify its content dynamically
        ObjectNode cartNode = (ObjectNode) cartData;

        // Add the current date dynamically using a utility method
        cartNode.put("date", TestUtils.getCurrentDate());

        // Step 3: Send a POST request to the /carts endpoint with the JSON payload
        Response response = given()
                .basePath("/carts")
                .contentType(JSON)
                .body(cartNode.toString())
                .when()
                .post();

        // Print the response body in the console for verification
        response.prettyPrint();

        // Step 4: Validate the response
        // Check if status code is 200 or 201 (Success)
        int statusCode = response.getStatusCode();
        assertThat(statusCode, isOneOf(200, 201));

        // Verify that the returned userId matches the one sent in the request
        int returnedUserId = response.jsonPath().getInt("userId");
        assertThat(returnedUserId, equalTo(3));

        // Ensure that the products list is not empty in the response
        assertThat(response.jsonPath().getList("products").size(), greaterThan(0));
    }
}

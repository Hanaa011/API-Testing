import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    @Test
    public void testApiResponseValidation() {
        // API Endpoint
        String apiUrl = "https://fakerestapi.azurewebsites.net/api/v1/Users";

        // Send GET request
        Response response = RestAssured.get(apiUrl);

        // Print response
        System.out.println("Response: " + response.asString());
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Headers: " + response.headers());

        // Validate response status code
        response.then().assertThat().statusCode(200);

        // Validate response headers
        response.then().assertThat()
                .header("Content-Type", "application/json; charset=utf-8; v=1.0")
                .header("Server", containsString("Kestrel"))
                .header("Transfer-Encoding", "chunked");

        System.out.println("All assertions passed successfully.");
    }
}
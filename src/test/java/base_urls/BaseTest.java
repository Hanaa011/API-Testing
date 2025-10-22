package base_urls;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup() {
        // Set Base URI for Fake Store API
        RestAssured.baseURI = "https://fakestoreapi.com";
    }
}

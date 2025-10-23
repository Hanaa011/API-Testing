package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

import static io.restassured.http.ContentType.JSON;

public class BookstoreBaseUrl {
    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp() {

        spec = new RequestSpecBuilder()
                .setBaseUri("https://bookstore.demoqa.com")
                .setContentType(JSON)
                .build();
    }
}

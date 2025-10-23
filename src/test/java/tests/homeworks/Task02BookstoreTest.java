package tests.homeworks;

import base_urls.BookstoreBaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.*;
import test_data.BookstoreTestData;
import util.ObjectMapperUtils;

import java.util.Collections;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Task02BookstoreTest extends BookstoreBaseUrl {

    static String userId;
    static String token;

    @Test(priority = 1)
    public void createUser() {
        // Create user payload
        CreateUserPojo user = new CreateUserPojo(BookstoreTestData.USERNAME, BookstoreTestData.PASSWORD);

        Response response = given(spec)
                .body(user)
                .when()
                .post("/Account/v1/User");

        response.prettyPrint();

        // Extract userId from response
        userId = response.jsonPath().getString("userID");

        response.then().statusCode(201)
                .body("username", equalTo(BookstoreTestData.USERNAME));
    }

    @Test(priority = 2)
    public void generateToken() {
        CreateUserPojo login = new CreateUserPojo(BookstoreTestData.USERNAME, BookstoreTestData.PASSWORD);

        Response response = given(spec)
                .body(login)
                .when()
                .post("/Account/v1/GenerateToken");

        response.prettyPrint();

        TokenResponsePojo tokenPojo = ObjectMapperUtils.convertJsonToJava(response.asString(), TokenResponsePojo.class);
        token = tokenPojo.getToken();

        response.then().statusCode(200)
                .body("status", equalTo("Success"));
    }

    @Test(priority = 3)
    public void getAllBooks() {
        Response response = given(spec)
                .when()
                .get("/BookStore/v1/Books");

        response.prettyPrint();

        response.then().statusCode(200)
                .body("books.size()", greaterThan(0));
    }

    @Test(priority = 4)
    public void assignBookToUser() {
        BookAssignPojo.Isbn book = new BookAssignPojo.Isbn(BookstoreTestData.SAMPLE_ISBN);
        BookAssignPojo body = new BookAssignPojo(userId, Collections.singletonList(book));

        Response response = given(spec)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post("/BookStore/v1/Books");

        response.prettyPrint();

        response.then().statusCode(201);
    }

    @Test(priority = 5)
    public void getUserInfo() {
        Response response = given(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("id", userId)
                .when()
                .get("/Account/v1/User/{id}");

        response.prettyPrint();

        response.then().statusCode(200)
                .body("userId", equalTo(userId))
                .body("books.size()", greaterThanOrEqualTo(1));
    }
}

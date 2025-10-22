package tests.tasks;

import base_urls.GoRestBaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.GoRestPojo;
import util.ObjectMapperUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.AssertJUnit.assertEquals;

public class Task01UserManagement extends GoRestBaseUrl {

    /*
    Task 1: GoREST API - User Management Endpoints
    Using the API documentation at https://gorest.co.in/ :
    - Get all users
    - Create a user
    - Get that user
    - Update user
    - Partial Update User
    - Delete User
    - Get User Negative
     */

    static int userId;

    @Test
    public void getallUsers() {
        //Set the url
        spec.pathParam("first", "users");

        //Set the expected data

        //Send the request and get the response
        Response response = given(spec).get("{first}");
        response.prettyPrint();

        //Do assertion
        response
                .then()
                .statusCode(200)
        .body("[0].id", notNullValue());
    }

    @Test
    public void createUser() {
        //Set the url
        spec.pathParam("first", "users");

        //Set the expected data
        GoRestPojo expectedData = new GoRestPojo("Shahd Alghamdi", "shahd@example.com", "female", "active");
        System.out.println("expectedData = " + expectedData);

        //Send the request and get the response
        Response response = given(spec).body(expectedData).post("{first}");
        response.prettyPrint();

        //Do assertion
        response.then().statusCode(201);

        //Extract the user id for later use
        userId = response.jsonPath().getInt("id");
        System.out.println("userId = " + userId);
    }

    @Test(dependsOnMethods = "createUser")
    public void getUser() {
        //Set the url
        spec.pathParams("first", "users", "second", userId);

        //Set the expected data
        GoRestPojo expectedData = new GoRestPojo("Shahd Alghamdi", "shahd@example.com", "female", "active");
        System.out.println("expectedData = " + expectedData);

        //Send the request and get the response
        Response response = given(spec).get("{first}/{second}");
        response.prettyPrint();

        //Do assertion
        response.then().statusCode(200);

        GoRestPojo actualData = ObjectMapperUtils.convertJsonToJava(response.asString(), GoRestPojo.class);
        System.out.println("actualData = " + actualData);

        assertEquals(expectedData.getName(), actualData.getName());
        assertEquals(expectedData.getEmail(), actualData.getEmail());
        assertEquals(expectedData.getGender(), actualData.getGender());
        assertEquals(expectedData.getStatus(), actualData.getStatus());
    }

    @Test(dependsOnMethods = "getUser")
    public void updateUser() {
        //Set the url
        spec.pathParams("first", "users", "second", userId);

        //Set the expected data
        GoRestPojo expectedData = new GoRestPojo("Shahd Ahmed", "shahd.ahmed@example.com", "female", "active");
        System.out.println("expectedData = " + expectedData);

        //Send the request and get the response
        Response response = given(spec).body(expectedData).put("{first}/{second}");
        response.prettyPrint();

        //Do assertion
        response.then().statusCode(200);

        GoRestPojo actualData = ObjectMapperUtils.convertJsonToJava(response.asString(), GoRestPojo.class);
        System.out.println("actualData = " + actualData);

        assertEquals(expectedData.getName(), actualData.getName());
        assertEquals(expectedData.getEmail(), actualData.getEmail());
        assertEquals(expectedData.getGender(), actualData.getGender());
        assertEquals(expectedData.getStatus(), actualData.getStatus());
    }

    @Test(dependsOnMethods = "updateUser")
    public void partialUpdateUser() {
        //Set the url
        spec.pathParams("first", "users", "second", userId);

        //Set the expected data
        String expectedName = "Shahd Ali";
        String expectedStatus = "inactive";

        //Send the request and get the response
        String requestBody = "{\"name\":\"" + expectedName + "\", \"status\":\"" + expectedStatus + "\"}";
        Response response = given(spec).body(requestBody).patch("{first}/{second}");
        response.prettyPrint();

        //Do assertion
        response.then().statusCode(200);

        assertEquals(expectedName, response.jsonPath().getString("name"));
        assertEquals(expectedStatus, response.jsonPath().getString("status"));
    }

    @Test(dependsOnMethods = "partialUpdateUser")
    public void deleteUser() {
        //Set the url
        spec.pathParams("first", "users", "second", userId);

        //Send the request and get the response
        Response response = given(spec).delete("{first}/{second}");
        response.prettyPrint();

        //Do assertion
        response.then().statusCode(204);
    }

    @Test(dependsOnMethods = "deleteUser")
    public void getUserNegative() {
        //Set the url
        spec.pathParams("first", "users", "second", userId);

        //Send the request and get the response
        Response response = given(spec).get("{first}/{second}");
        response.prettyPrint();

        //Do assertion
        response.then().statusCode(404);
    }
}
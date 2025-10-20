package tests.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class Test02 {

    @Test
    public void testTodos() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/todos");

        response.then().statusCode(200);

        List<Integer> ids = response.jsonPath().getList("id");
        System.out.println("IDs > 190:");
        for (int id : ids) {
            if (id > 190) {
                System.out.println(id);
            }
        }

        System.out.println("\nUser IDs where id < 5 :");
        List<Integer> allIds = response.jsonPath().getList("id");
        List<Integer> userIds = response.jsonPath().getList("userId");
        for (int i = 0; i < allIds.size(); i++) {
            if (allIds.get(i) < 5) {
                System.out.println("userId: " + userIds.get(i));
            }
        }

        response.then().body("title", hasItem("quis eius est sint explicabo"));

        int id = response.jsonPath().getInt("find { it.title == 'quo adipisci enim quam ut ab' }.id");
        System.out.println("\nID for title 'quo adipisci enim quam ut ab' = " + id);
    }
}

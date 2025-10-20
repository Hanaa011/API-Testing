package tests.tasks;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class Test03 {

    @Test
    public void testEmployees() {

        Response response = RestAssured.get("https://dummy.restapiexample.com/api/v1/employees");

        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();

        // Check there are 24 employees
        int employeeCount = jsonPath.getInt("data.size()");
        System.out.println("Total employees: " + employeeCount);
        assertEquals(employeeCount, 24);

        // Check if "Tiger Nixon" and "Garrett Winters" are among them
        List<String> employeeNames = jsonPath.getList("data.employee_name");
        System.out.println("Tiger Nixon exists: " + employeeNames.contains("Tiger Nixon"));
        System.out.println("Garrett Winters exists: " + employeeNames.contains("Garrett Winters"));

        // Find highest age
        int highestAge = jsonPath.getInt("data.employee_age.max()");
        System.out.println("Highest age: " + highestAge);
        assertEquals(highestAge, 66);

        // Find youngest employee
        String youngestEmployee = jsonPath.getString("data.min { it.employee_age }.employee_name");
        System.out.println("Youngest employee: " + youngestEmployee);
        assertEquals(youngestEmployee, "Tatyana Fitzpatrick");

        // Find total salary
        int totalSalary = jsonPath.getInt("data.collect{it.employee_salary.toInteger()}.sum()");
        System.out.println("Total salary: " + totalSalary);
        assertEquals(totalSalary, 6644770);
    }
}
package POJO;

import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Collections;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class ApiKeyTest {
    @Test
    void apiKeyTest(){
        given()
                .header("x-api-key","GwMco9Tpstd5vbzBzlzW9I7hr6E1D7w2zEIrhOra")

                .when()
                .get("https://l9njuzrhf3.execute-api.eu-west-1.amazonaws.com/prod/user")

                .then()
                .log().body()
                .statusCode(200);

    }
}
package POJO;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Tasks {
    @Test
    public void task1(){
        Todo todo=
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .extract().as(Todo.class);

        System.out.println("todo"+todo);
    }
    @Test
    public void task2(){

        given()

                .when()
                .get("https://httpstat.us/203")

                .then()
                .log().body()
                .statusCode(203)
                .contentType(ContentType.TEXT)
        ;

    }
    @Test
    public void task3(){

        given()

                .when()
                .get("https://httpstat.us/203")

                .then()
                .log().body()
                .statusCode(203)
                .contentType(ContentType.TEXT)
                .body(equalTo("203 Non-Authoritative Information"))
                .extract().body()
        ;

        //2.Yontem

        String bodyText=
                given()
                .when()
                .get("https://httpstat.us/203")

                .then()
                .log().body()
                .statusCode(203)
                .contentType(ContentType.TEXT)
                .extract().body().asString();
        Assert.assertTrue(bodyText.equalsIgnoreCase("203 Non-Authoritative Information"));

    }
    @Test
    public void task4(){

                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("title",equalTo("quis ut nam facilis et officia qui"));


    }
    @Test
    public void task5(){

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .body("completed",equalTo(false));


        //2.Yontem

        Boolean completed=
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .extract().path("completed");
        Assert.assertFalse(completed);
    }

}

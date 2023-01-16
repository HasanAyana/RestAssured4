package GoRest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUsersTests {

    int userID;
    User newUser;

    @BeforeClass
    void Setup() {
        baseURI = "https://gorest.co.in/public/v2/users"; // PROD
        // baseURI = "https://test.gorest.co.in/public/v2/users";  // TEST
    }

    public String getRandomName() {  return RandomStringUtils.randomAlphabetic(8); }

    public String getRandomEmail() { return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com"; }

    @Test(enabled = false)
    public void createUserObject()
    {
        // başlangıç işlemleri
        // token aldım
        // users JSON ı hazırladım.
        int userID=
                given()
                        .header("Authorization", "Bearer 4ce103e0d1147cd48c8d5d9e88888512b112da139c5c49cac73de9cf7e7c2d29")
                        .contentType(ContentType.JSON)
                        .body("{\"name\":\""+getRandomName()+"\", \"gender\":\"male\", \"email\":\""+getRandomEmail()+"\", \"status\":\"active\"}")
                        // üst taraf request özellikleridir : hazırlık işlemleri POSTMAN deki Authorization ve request BODY kısmı
                        .log().uri()
                        .log().body()
                        .when() // request in olduğu nokta    POSTMAN deki SEND butonu
                        .post("") //baseURI+paratez içi(http yoksa) respons un oluştuğu nokta
                        // CREATE işlemi POST metodu ile çağırıyoruz POSTMAN deki gibi

                        // alt taraf response sonrası  POSTMAN deki test penceresi
                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
                ;

        System.out.println("userID = " + userID);
    }

    @Test(enabled = false)
    public void createUserObject2WithMap()
    {
        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",getRandomName());
        newUser.put("gender","male");
        newUser.put("email",getRandomEmail());
        newUser.put("status","active");

        int userID=
                given()
                        .header("Authorization", "Bearer 4ce103e0d1147cd48c8d5d9e88888512b112da139c5c49cac73de9cf7e7c2d29")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().body()

                        .when()
                        .post("")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
                ;

        System.out.println("userID = " + userID);
    }

    @Test
    public void createUserObject3WithObject()
    {
        newUser=new User();
        newUser.setName(getRandomName());
        newUser.setGender("male");
        newUser.setEmail(getRandomEmail());
        newUser.setStatus("active");

        userID=
                given()
                        .header("Authorization", "Bearer 4ce103e0d1147cd48c8d5d9e88888512b112da139c5c49cac73de9cf7e7c2d29")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().body()

                        .when()
                        .post("")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        //.extract().path("id")
                        .extract().jsonPath().getInt("id");
        ;
        System.out.println("userID = " + userID);

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
    }

    @Test(dependsOnMethods = "createUserObject3WithObject", priority = 1)
    public void getUserByID()
    {
        given()
                .header("Authorization", "Bearer 4ce103e0d1147cd48c8d5d9e88888512b112da139c5c49cac73de9cf7e7c2d29")
                .pathParam("userId",userID)
                .log().uri()

                .when()
                .get("/{userId}")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(userID))
        ;
    }

    @Test(dependsOnMethods = "createUserObject3WithObject", priority = 2)
    public void updateUserObject()
    {
        newUser.setName("ismet t12emur");

        //Map<String,String> updateUser=new HashMap<>();
        //updateUser.put("name", "ismet t1emur");

        given()
                .header("Authorization", "Bearer 4ce103e0d1147cd48c8d5d9e88888512b112da139c5c49cac73de9cf7e7c2d29")
                .pathParam("userId",userID)
                .contentType(ContentType.JSON)
                .body(newUser)
                .log().body()
                .log().uri()

                .when()
                .put("/{userId}")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(userID))
                .body("name",equalTo("ismet t12emur"))
        ;
    }

    @Test(dependsOnMethods ="updateUserObject", priority = 3)
    public void deleteUserById()
    {

        given()
                .header("Authorization", "Bearer 4ce103e0d1147cd48c8d5d9e88888512b112da139c5c49cac73de9cf7e7c2d29")
                .pathParam("userId",userID)
                .log().uri()

                .when()
                .delete("/{userId}")

                .then()
                .log().body()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "deleteUserById")
    public void deleteUserByIdNegative()
    {

        given()
                .header("Authorization", "Bearer 4ce103e0d1147cd48c8d5d9e88888512b112da139c5c49cac73de9cf7e7c2d29")
                .pathParam("userId",userID)
                .log().uri()

                .when()
                .delete("/{userId}")

                .then()
                .log().body()
                .statusCode(404)
        ;
    }
@Test
public void getUser(){
        Response body=
                given()
                        .header("Authorization", "Bearer 4ce103e0d1147cd48c8d5d9e88888512b112da139c5c49cac73de9cf7e7c2d29")
                        .when()
                        .get()
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();

                int idUser3path= body.path("[2].id");
                int idUser3JsonPath= body.jsonPath().getInt("[2].id");
    System.out.println("idUser3path = " + idUser3path);
    System.out.println("idUser3JsonPath = " + idUser3JsonPath);
}



}

class User{
    private String name;
    private String gender;
    private String email;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

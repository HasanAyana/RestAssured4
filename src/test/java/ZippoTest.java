import POJO.Location;
import io.restassured.builder.*;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;


    @Test
    public void test() {
        given()
                // hazırlık işlemlerini yapacağız (token,send body, parametreler)
                .when()
                // link i ve metodu veriyoruz
                .then()
        //  assertion ve verileri ele alma extract
        ;
    }

    @Test
    public void statusCodeTest() {
        http:
//api.zippopotam.us/us/90210
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() bütün responsu gösterir
                .statusCode(200)
        ;
    }

    @Test
    public void contentTypeTest() {
        http:
//api.zippopotam.us/us/90210
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // log().all() bütün responsu gösterir
                .statusCode(200)
                .contentType(ContentType.JSON) // dönen tip json tipindemi

        ;
    }

    @Test
    public void checkCountryInResponseBody() {
        http:
//api.zippopotam.us/us/90210
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // log().all() bütün responsu gösterir
                .statusCode(200)
                .body("country", equalTo("United States"))

        ;
    }

    @Test
    public void checkStateInResponseBody() {
        http:
//api.zippopotam.us/us/90210
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // log().all() bütün responsu gösterir
                .statusCode(200)
                .body("places[0].'state'", equalTo("California"))

        ;
    }

    @Test
    public void bodyJsonPathTest3() {
        http:
//api.zippopotam.us/us/90210
        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")


                .then()
                .log().body() // log().all() bütün responsu gösterir
                .statusCode(200)
                .body("places.'place name'", hasItem("Dörtağaç Köyü"))
        ;
    }

    @Test
    public void bodyArrayHasSizeTest() {
        http:
//api.zippopotam.us/us/90210
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // log().all() bütün responsu gösterir
                .statusCode(200)
                .body("places", hasSize(1))

        ;
    }

    @Test
    public void combiningTest() {
        http:
//api.zippopotam.us/us/90210
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // log().all() bütün responsu gösterir
                .statusCode(200)
                .body("places", hasSize(1))
                .body("places.state", hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))

        ;
    }

    @Test
    public void pathParamTest() {
        http:
//api.zippopotam.us/us/90210
        given()
                .pathParam("Country", "us")
                .pathParam("Zipkod", 90210)
                .log().uri() // request link :  Request URI:	http://api.zippopotam.us/us/90210


                .when()
                .get("http://api.zippopotam.us/{Country}/{Zipkod}")


                .then()
                .log().body()

                .statusCode(200)


        ;
    }

    @Test
    public void pathParamTest2() {
        http:
//api.zippopotam.us/us/90210
        for (int i = 90210; i <= 90213; i++) {


            given()
                    .pathParam("Country", "us")
                    .pathParam("Zipkod", i)
                    .log().uri() // request link :  Request URI:	http://api.zippopotam.us/us/90210


                    .when()
                    .get("http://api.zippopotam.us/{Country}/{Zipkod}")


                    .then()
                    .log().body()

                    .statusCode(200)
                    .body("places", hasSize(1))


            ;
        }
    }

    @Test
    public void queryParamTest() {
        https:
//gorest.co.in/public/v1/users?page=3
        given()
                .param("page", 1)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")


                .then()
                .log().body()
                .statusCode(200)
                .body("meta.pagination.page", equalTo(1))


        ;
    }

    @Test
    public void queryParamTest2() {
        https:
//gorest.co.in/public/v1/users?page=3

        for (int pageNo = 1; pageNo <= 10; pageNo++) {


            given()
                    .param("page", pageNo)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")


                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("meta.pagination.page", equalTo(pageNo))


            ;
        }
    }

    @Test
    public void test1() {
        //http://api.zippopotam.us/us/90210
        for (int i = 90210; i <= 90213; i++) {


            given()
                    .pathParam("Country", "us")
                    .pathParam("Zipcode", i)

                    .when()
                    .get("http://api.zippopotam.us/{Country}/{Zipcode}")

                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("places", hasSize(1))


            ;
        }
    }

    @Test
    public void queryParamTest3() {
        // https://gorest.co.in/public/v1/users?page=3

        for (int pageNo = 1; pageNo <= 10; pageNo++) {


            given()
                    .param("page", pageNo)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")


                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("meta.pagination.page", equalTo(pageNo))


            ;

        }
    }


    void Setup() {
        baseURI = "https://gorest.co.in/public/v1";
        requestSpec = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build()


        ;
    }

    @Test
    public void queryParamTest4() {
        // https://gorest.co.in/public/v1/users?page=3


        given()
                .param("page", 1)
                .spec(requestSpec)

                .when()
                .get("/users")


                .then()
                .body("meta.pagination.page", equalTo(1))
                .spec(responseSpec)


        ;


    }

    @Test
    public void extractingJsonPath() {
        String placeName =
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .statusCode(200)
                        .log().body()
                        .extract().path("places[0].'place name'");
        System.out.println("placeName = " + placeName);


    }

    @Test
    public void extractingJsonPathInt() {

        int limit =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")


                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("meta.pagination.limit");
        System.out.println("limit = " + limit);
        Assert.assertEquals(limit, 10, "test sonucu");
    }

    @Test
    public void extractingJsonPathList() {

        List<Integer> idler =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")


                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("data.id");
        System.out.println("limit = " + idler);
        Assert.assertTrue(idler.contains(4235));
    }

    @Test
    public void extractingJsonPathStringList() {

        List<String> names =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")


                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.name");
        System.out.println("limit = " + names);
        Assert.assertTrue(names.contains("Ayushmati Singh"));
    }

    @Test
    public void extractingJsonPathResponseAll() {

        Response response =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")


                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response();
        ;
        List<Integer> idler = response.path("data.id");
        List<String> names = response.path("data.name");
        int limit = response.path("meta.pagination.limit");
        System.out.println("limit = " + limit);
        System.out.println("idler = " + idler);
        System.out.println("names = " + names);
        System.out.println("response = " + response.prettyPrint());

        Assert.assertTrue(names.contains("Gautam Ahluwalia"));
        Assert.assertTrue(idler.contains(4235));
        Assert.assertEquals(limit, 10, "Test sonucu");
    }

    @Test
    public void extractingJsonPath1() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .statusCode(200)
                .log().body()

        ;

    }

    @Test
    public void extractingJsonPath2() {

        Location yer =
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().as(Location.class);
        System.out.println("yer.getPostCode() = " + yer.getPostCode());
        System.out.println("yer.getPlaces().get(0).getPlaceName() = " + yer.getPlaces().get(0).getPlaceName());
    }
}

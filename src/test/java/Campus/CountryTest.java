package Campus;

import Campus.Model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryTest {
    Cookies cookies;
    String countryID;
    String countryName;
    String countryCode;
    @BeforeClass
    public void loginCampus()
    {


        baseURI="https://test.mersys.io/";
        //Diğer testler çalışmadan önce login ol
        //Bu yüzden beforeClass a annotation eklendi
        Map<String,String> credential=new HashMap<>();
        credential.put("username","turkeyts");
        credential.put("password","TechnoStudy123");
        credential.put("rememberMe","true");
        cookies=
        given()
                .contentType(ContentType.JSON)
                .body(credential)

                .when()
                .post("auth/login")

                .then()
                .log().body()
                .statusCode(200)
                .extract().response().getDetailedCookies()

                ;




    }
    public String getRandomName() {  return RandomStringUtils.randomAlphabetic(8); }

    public String getRandomEmail() { return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com"; }
    public String getRandomCode() { return RandomStringUtils.randomAlphabetic(3); }


    @Test
    public void createCountry(){
        countryName=getRandomName();
        countryCode=getRandomCode();
        Country country=new Country();
        country.setName(countryName);
        country.setCode(countryCode);

        countryID=
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .post("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id");

    }
    @Test(dependsOnMethods = "createCountry" ,priority = 1)
    public void createCountryNegative(){

        Country country=new Country();
        country.setName(countryName);
        country.setCode(countryCode);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .post("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("The Country with Name \""+countryName+"\" already exists."))
                .body("message",containsString("already exists."))
        ;


    }
    @Test(dependsOnMethods = "createCountry",priority = 2)
    public void updateCountry(){
        countryName=getRandomName();
        countryCode=getRandomCode();

        Country country=new Country();
        country.setId(countryID);
        country.setName(countryName);
        country.setCode(countryCode);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .put("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(countryName))

        ;

    }
    @Test(dependsOnMethods = "updateCountry" )
    public void DeleteCountryById(){


        given()

                .cookies(cookies)
                .pathParam("countryID",countryID)
                .log().uri()



                .when()
                .delete("school-service/api/countries/{countryID}")

                .then()
                .log().body()
                .statusCode(200)


        ;

    }
    @Test(dependsOnMethods = "DeleteCountryById" )
    public void DeleteCountryByIdNegative(){


        given()

                .cookies(cookies)
                .pathParam("countryID",countryID)
                .log().uri()



                .when()
                .delete("school-service/api/countries/{countryID}")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Country not found"))


        ;

    }
    @Test(dependsOnMethods = "DeleteCountryById")
    public void updateCountryAgain(){
        countryName=getRandomName();
        countryCode=getRandomCode();

        Country country=new Country();
        country.setId(countryID);
        country.setName(countryName);
        country.setCode(countryCode);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .put("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Country not found"))

        ;

    }


}

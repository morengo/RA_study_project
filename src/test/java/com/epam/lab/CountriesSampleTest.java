package com.epam.lab;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;

public class CountriesSampleTest {

    private static final String GET_ALL = "/get/all";
    private static final String GET_ISO_CODE = "/get/iso2code/{alpha2_code}";

    @BeforeClass
    public void before(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://services.groupkt.com")
                .setBasePath("/country")
                .setContentType(ContentType.ANY)
                .setAccept(ContentType.ANY)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test(dataProvider = "")
    public void checkTotalRecords(String basePath){

        RequestSpecification requestSpecification1 = given().basePath(basePath);

        given().spec(requestSpecification1)
                .when()
                .get(GET_ALL)
                .then().log().all()
                .statusCode(200)
                .body("RestResponse.messages[0]", Matchers.equalTo("Total [249] records found."))
                .and().body("RestResponse.result[2].name", Matchers.equalTo("Albania"))
                .and().body("RestResponse.result.name", Matchers.hasItems("Andorra", "Anguilla"));



    }

    @Test
    public void checkAlbaniaIsPresent(){

        Response response = given()
                .when()
                .get(GET_ISO_CODE, "AL");

                response.then()
                        .statusCode(HTTP_OK)
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .log().all()
                        .assertThat().body("RestResponse.result.name", Matchers.containsString("Alb"),
                        "RestResponse.result.alpha2_code", Matchers.equalTo("AL"));



    }
}



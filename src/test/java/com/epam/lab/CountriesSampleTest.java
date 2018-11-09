package com.epam.lab;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.MatcherAssert.assertThat;

public class CountriesSampleTest extends BaseSampleTest {

    private static final String GET_ALL = "/get/all";
    private static final String GET_ISO_CODE = "/get/iso2code/{alpha2_code}";

    @DataProvider
    public static Object[][] countries() {
        return new Object[][]{
                {"AL", "Alb"},
                {"WF", "Wallis"},
                {"UA", "Ukr"}
        };
    }

    @Test
    public void checkTotalRecords() {

        given()
                .when()
                .get(GET_ALL)
                .then().log().all()
                .statusCode(200)
                .body("RestResponse.messages[0]", Matchers.equalTo("Total [249] records found."))
                .and().body("RestResponse.result[2].name", Matchers.equalTo("Albania"))
                .and().body("RestResponse.result.name", Matchers.hasItems("Andorra", "Anguilla"));
    }

    @Test (dataProvider = "countries")
    public void checkCountriesIsPresent(String alfa2code, String countryNameStartsWith) {

        Response response = given()
                .when()
                .get(GET_ISO_CODE, alfa2code);

        response.then()
                .statusCode(HTTP_OK)
                .header("Content-Type", "application/json;charset=UTF-8")
                .assertThat().body("RestResponse.result.name", Matchers.containsString(countryNameStartsWith),
                "RestResponse.result.alpha2_code", Matchers.equalTo(alfa2code));
    }

    @Test (dataProvider = "countries")
    public void checkCountryAsClass(String alfa2code, String countryNameStartsWith) {

        Response response = given().when().get(GET_ISO_CODE, alfa2code);
        Country country = response.as(RequestResponseWrapper.class).getRestResponse().getCountry();
        List<String> messages = response.as(RequestResponseWrapper.class).getRestResponse().getMessages();

        assertThat(country.getName(), Matchers.containsString(countryNameStartsWith));
        assertThat(messages.size(), Matchers.equalTo(1));
    }
}



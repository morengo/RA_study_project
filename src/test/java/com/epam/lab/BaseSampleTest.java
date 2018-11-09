package com.epam.lab;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeSuite;

public class BaseSampleTest {

    @BeforeSuite
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
}
package com.iqvia.practicalAssessment.spec;

import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import com.iqvia.practicalAssessment.constants.*;
import com.iqvia.practicalAssessment.helpers.CountriesActions;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus; 

public class CapitalApi {	
	@Test(testName = "Verify capital API response for an exisiting capital")
	void verify_api_response_for_existing_capital() {
		String capitalUrl = "/"+Urls.CAPITAL_FIELD;
		String[] fields = new String[] {Urls.NAME_FIELD, Urls.CAPITAL_FIELD, Urls.CUURENCIES_FIELD, Urls.LATLNG_FIELD};

		JsonPath body = CountriesActions.getAllCountries(fields).then().extract().jsonPath();

		// 2 is the index of Capital - Tirana
		String capital = body.getString("capital[2]");
		List<Map<String, String>> currencies = body.getList("currencies[2]");

		RestAssured.baseURI = Urls.BASE_URL;
		capitalUrl += "/"+capital+"?fields="+Urls.NAME_FIELD+";"+Urls.CAPITAL_FIELD+";"+Urls.CUURENCIES_FIELD+";"+Urls.LATLNG_FIELD;

		given()
			.get(capitalUrl)
		.then()
		.assertThat()
			.statusCode(HttpStatus.SC_OK)
		.and()
			.body(matchesJsonSchemaInClasspath("schemas/ExistingCapital.json"))
		.and()
			.body("currencies[0]", equalTo(currencies));
	}
	
	@Test(testName = "Verify capital API response for non exisiting capital")
	void verify_api_response_schema_for_non_existing_capital() {
		String nonExistingCapital = "Majdi";
		String capitalUrl = "/"+Urls.CAPITAL_FIELD+"/"+nonExistingCapital+"?fields="+Urls.NAME_FIELD+";"+Urls.CAPITAL_FIELD+";"+Urls.CUURENCIES_FIELD+";"+Urls.LATLNG_FIELD;

		RestAssured.baseURI = Urls.BASE_URL;
		
		given()
	   		.get(capitalUrl)
   		.then()
   		.assertThat()
	   		.statusCode(HttpStatus.SC_NOT_FOUND)
   		.and()
   			.body(matchesJsonSchemaInClasspath("schemas/NotFoundResponseSchema.json"));		
	}
}

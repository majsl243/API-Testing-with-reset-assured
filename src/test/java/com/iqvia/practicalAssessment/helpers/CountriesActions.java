package com.iqvia.practicalAssessment.helpers;

import com.iqvia.practicalAssessment.constants.Urls;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CountriesActions {

	/**
	 * A function to return all countries list
	 * @param fields a string array that contains the required fields that should return with the response
	 * @return JSON array with all counties with the fields that were specified earlier
	 */
	public static Response getAllCountries(String [] fields) {
		String url = Urls.BASE_URL+"/"+Urls.ALL+"?";
		
		if (fields != null && fields.length > 0) {
			
			String fieldsString = "";
			for (int i = 0; i< fields.length; i++) {
				fieldsString += fields[i]+";";
			}
			url += "fields="+fieldsString;
		}

		return RestAssured.get(url);
	}
	
	/**
	 * 
	 * @param capital the capital to return details for
	 * @param fields a string array that contains the required fields that should return with the response
	 * @return response containing capital details
	 */
	public static Response getCapitalDetails(String capital,String [] fields) {
		String url = Urls.BASE_URL+"/"+Urls.CAPITAL_FIELD+"/"+capital+"?";
		
		if (fields != null && fields.length > 0) {
			
			String fieldsString = "";
			for (int i = 0; i< fields.length; i++) {
				fieldsString += fields[i]+";";
			}
			url += "fields="+fieldsString;
		}
		
		return RestAssured.get(url);
	}
}

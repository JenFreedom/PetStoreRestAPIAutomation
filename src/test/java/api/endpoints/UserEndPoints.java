package api.endpoints;

import static io.restassured.RestAssured.*;

import java.util.ResourceBundle;

import api.payload.User;
import api.utilities.Routes;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


// Created to perform CRUD flow - Create, Read, Update, Delete request

public class UserEndPoints {
//	protected static String baseURL;
	
	// method to get URL from properties file
	static ResourceBundle getURL(){
		ResourceBundle routes = ResourceBundle.getBundle("routes"); // load the properties file (routes - property filename)
		return routes;
	}
	
	
	public static Response createUser(User payload, RequestSpecification reqSpec) {
		Response response = given(reqSpec)
			.body(payload)
		.when()
			.post(Routes.POST_URL);
		
		return response;
	}
	
	public static Response readUser(String userName,RequestSpecification reqSpec) {
		
		Response response = given(reqSpec)
			.pathParam("username", userName)
		.when()
			.get(Routes.GET_URL);
		
		return response;
	}

	public static Response updateUser(String userName, User payload,RequestSpecification reqSpec) {
		
		Response response = given(reqSpec)
			.body(payload)
			.pathParam("username", userName)
		.when()
			.put(Routes.UPDATE_URL);
		
		return response;
	}
	
	public static Response deleteUser(String userName,RequestSpecification reqSpec) {
		Response response = given(reqSpec)
			.pathParam("username", userName)
			
		.when()
			.delete(Routes.DELETE_URL);
		
		return response;
	}


}

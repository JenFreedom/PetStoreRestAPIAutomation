package api.test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class userTests {
	
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setupData() {
	   
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	}
	@Test(priority=1)
	public void testPostUser(){
		System.out.println("Post User");
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		System.out.println("Get User");
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		System.out.println("Update User");
		// update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
	//	response.then().log().body().statusCode(200);
		response.then().log().body();
	//	System.out.println("PrintUpdated");
	//	response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		// Checking data after update
		Response updated_response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(updated_response.getStatusCode(), 200);
		
	}
	
	@Test(priority =4)
	public void testDeleteUserByName() {
		System.out.println("Delete User");
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());		
		Assert.assertEquals(response.getStatusCode(),200);
	}
}

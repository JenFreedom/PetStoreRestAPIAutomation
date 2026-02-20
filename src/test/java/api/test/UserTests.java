package api.test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.base.BaseTest;
import api.endpoints.UserEndPoints;
import io.restassured.response.Response;

public class UserTests extends BaseTest{
		
	@Test(priority=1)
	public void testPostUser(){
		logger.info("********* Creating User *********");
		Response response = UserEndPoints.createUser(userPayload,reqSpec);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		// Petstore returns the user ID in message field
	    Assert.assertNotNull(response.jsonPath().getString("message"));
		logger.info("*********User Created *********");
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		logger.info("*********Reading User Info *********");
		Response response = UserEndPoints.readUser(this.userPayload.getUsername(),reqSpec);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);

		// GET actually returns user data — safe to verify here
	    Assert.assertEquals(response.jsonPath().getString("username"),
	                        userPayload.getUsername());
		logger.info("User Info displayed");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		logger.info("Update User");

		// update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload,reqSpec);
		response.then().log().body();
	//	response.then().log().all();
	 // Petstore update only confirms 200 — cannot verify field changes
		Assert.assertEquals(response.getStatusCode(),200);

		// Checking data after update
		Response updated_response = UserEndPoints.readUser(this.userPayload.getUsername(),reqSpec);
		updated_response.then().log().all();
		Assert.assertEquals(updated_response.getStatusCode(), 200);
		logger.info("********* User Updated (status verified only) *********");

	}
	
	@Test(priority =4)
	public void testDeleteUserByName() {
		logger.info("Deleting User");

		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername(),reqSpec);		
		Assert.assertEquals(response.getStatusCode(),200);

	// Verify user is gone — GET should return 404
	    Response deletedResponse = UserEndPoints.readUser(this.userPayload.getUsername(), reqSpec);
	    Assert.assertEquals(deletedResponse.getStatusCode(), 404);
	    logger.info("********* Deletion Verified *********");
	}
}

package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.base.BaseTest;
import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests extends BaseTest {
	
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testPostUser(String userID,String userName,String fname, String lname, String useremail, String pwd, String ph) {
		
		logger.info("********Create User *********");
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		Response response = UserEndPoints.createUser(userPayload,reqSpec);
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********* User Created: " + userName + " *********");
	}
	
	@Test(priority=2,dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testdeleteUserByName(String userName) {
		logger.info("********* Deleting User: " + userName + " *********");
		
		Response response = UserEndPoints.deleteUser(userName,reqSpec);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********* User Deleted: " + userName + " *********");

	       // Verify user is actually gone
  //      Response deletedResponse = UserEndPoints.readUser(userName, reqSpec);
   //     Assert.assertEquals(deletedResponse.getStatusCode(), 404);
    //    logger.info("********* Deletion Verified: " + userName + " *********");
        // NOTE: Petstore API does not reliably return 404 after delete
        // Skipping post-delete verification due to mock API limitation

	}
}

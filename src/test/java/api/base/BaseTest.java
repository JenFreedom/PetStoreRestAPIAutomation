package api.base;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import io.restassured.specification.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import com.github.javafaker.Faker;

import api.payload.User;
import api.utilities.Routes;

public class BaseTest {
	protected Faker faker;
	protected User userPayload;
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	protected RequestSpecification reqSpec;
	
	@BeforeClass
	public void setup() {
	
		logger.info("*********Initializing test setup*******");

		faker = new Faker();
		userPayload = new User();

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger.info("Test Data initialized successfully: "+userPayload.toString());
		
		// Initialize Request Spec
		String baseURL = Routes.baseURL;
		
		reqSpec = new RequestSpecBuilder()
				.setBaseUri(baseURL)
				.setContentType(ContentType.JSON)
				.addHeader("Accept", ContentType.JSON.toString())
				.build();
				
		logger.info("BaseURL : " + baseURL  );
		logger.info("********** Set up Complete **********");

	}

		
}


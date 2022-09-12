package api_project.scratchpay;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class Test1 {

	
	@Test
	public void testUserNotAbleToGetListOfEmailAddresses() {

		// getting token
		String bearerToken = getJWT();
		String emailAddressUrl = "https://qa-challenge-api.scratchpay.com/api/clinics/3/emails";
		
		Response response = given().headers(
	              "Authorization",
	              "Bearer " + bearerToken,
	              "Content-Type",
	              ContentType.JSON,
	              "Accept",
	              ContentType.JSON)
	          .when()
	          .get(emailAddressUrl);
		
		JsonPath jsonPath = response.jsonPath();
		String actualErrorMessage = jsonPath.get("data.error");
		//System.out.print(actualErrorMessage);
		// getting error message
		String expectedErrorMessage = "Error: User does not have permissions";
		// validating error message
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
		
	}

	@Test
	public void test() {
     String clinicsUrl = "https://qa-challenge-api.scratchpay.com/api/clinics";
     String bearerToken = getJWT();
     Response response = given().headers(
             "Authorization",
             "Bearer " + bearerToken,
             "Content-Type",
             ContentType.JSON,
             "Accept",
             ContentType.JSON).queryParam("term", "veterinary")
         .when()
         .get(clinicsUrl);
      
      JsonPath jsonpath = response.jsonPath();
      int statusCode = response.getStatusCode();
      //Asserting status code
      Assert.assertEquals(statusCode, 200);
      //Asserting data is not null
      Assert.assertNotNull(jsonpath.get("data"));
      // Clinic Name Validation
      String clinicname1 = jsonpath.get("data[0].displayName");
      String clinicname2 = jsonpath.get("data[1].displayName");
      Assert.assertEquals(clinicname1,"Continental Veterinary Clinic, Los Angeles, CA");
      Assert.assertEquals(clinicname2,"Third Transfer Veterinary Clinic, Los Angeles, CA");
      
	}

	public static String getJWT() {
		String jwtUrl = "https://qa-challenge-api.scratchpay.com/api/auth";
		Response response = given().queryParam("email", "gianna@hightable.test").queryParam("password", "thedantonio1")
				.when().get(jwtUrl);
	//	String responseBody = response.getBody().asString();

		JsonPath jsonpath = response.jsonPath();
		String token = jsonpath.get("data.session.token");

		return token;
	}
}

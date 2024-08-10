package api_test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api_endpoints.api_endpoints;
import api_payload.User;
import io.restassured.response.Response;

public class userTest {
	
	Faker faker;
	User userpayload;
	
	@BeforeClass
	public void setupData() {
		faker=new Faker();
		userpayload=new User();
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());	
	}
	@Test(priority=1)
	public void testPostUser(){
		Response response = api_endpoints.createUser(userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
	}
	@Test(priority=2)
	public void testGetUserByName() {
		Response response=api_endpoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
	}
	@Test(priority=3)
	public void testUpdateUserByName()
	{	//update data using payload
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response=api_endpoints.updateUser(this.userpayload.getUsername(),userpayload);
		response.then().log().all();
		//or response.then.log().body().statusCode(200);
		Assert.assertEquals(response.getStatusCode(),200);
		
		//checking data after update reequest 
		//by passing the getuser request
		Response responseAfterUpdateRequestByGetUser = api_endpoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(responseAfterUpdateRequestByGetUser.getStatusCode(),200);
	}
	@Test(priority=4)
	public void testDeleteUserByName() {
		Response response=api_endpoints.deleteUser(this.userpayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);	
	}
	
}

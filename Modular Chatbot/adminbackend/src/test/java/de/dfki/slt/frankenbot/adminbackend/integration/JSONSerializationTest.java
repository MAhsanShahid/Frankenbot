package de.dfki.slt.frankenbot.adminbackend.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import de.dfki.slt.frankenbot.adminbackend.service.CreateTestDataService;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
//@Sql(scripts = "classpath:db/simple-data.sql")
public class JSONSerializationTest {
	
	@LocalServerPort
	private int port;

	/**
	 * Test that the serialization correctly hides information annotated with 
	 * @JsonIgnore from the user.
	 * 
	 * @throws UnirestException
	 * @throws JSONException 
	 */
	@Test
	public void testSerialization() throws UnirestException, JSONException {
		
		String api = "http://localhost:" + port + "/api";
		HttpResponse<String> response = Unirest.get(api + "/user")
				.header("x-auth-token", CreateTestDataService.AUTH_TOKEN).asString();
		
		assertTrue(response.getStatus() == 200);
		JSONObject json = (JSONObject)(new JSONArray(response.getBody().toString()).get(0));
		assertFalse(json.has("salt"));
		assertFalse(json.has("password"));
	}
}

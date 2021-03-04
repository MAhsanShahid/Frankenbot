package de.dfki.slt.frankenbot.adminbackend.integration;

import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import de.dfki.slt.frankenbot.adminbackend.AdminBackendStarter;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=AdminBackendStarter.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = {"spring.config.name=myapp-test-h2","myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus"})
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationTest {
	
	@LocalServerPort
	private int port;
	
	/**
	 * Create a user, fetch an auth token. Then try to call list users without an
	 * invalid token and expect error. Then use the correct token and expect users.
	 * 
	 * @throws UnirestException
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	@Test
	public void testApi() throws UnirestException, JSONException, InterruptedException {
		
		// test authentication
		
		// create a user and authenticate
		String api = "http://localhost:" + port + "/api";

		String email = "klausi@gmx.de";
		String password = "geheim";
		String userJson = "{" + "\"name\": \"klaus\"," + "\"password\": \"" + password + "\"," + "\"email\": \"" + email
				+ "\"}";
		HttpResponse<String> response = Unirest.post(api + "/user").header("content-type", "application/json")
				.body(userJson).asString();

		JSONObject userJsonResponse = new JSONObject(response.getBody().toString());
		Integer userId = userJsonResponse.getInt("id");
		
		assertTrue(response.getStatus() == 200);

		response = Unirest.post(api + "/auth_token").header("X-Auth-Email", email)
				.header("X-Auth-Password", "falschespasswort").asString();
		assertTrue(response.getStatus() == 403);

		response = Unirest.post(api + "/auth_token").header("X-Auth-Email", email).header("X-Auth-Password", password)
				.asString();
		assertTrue(response.getStatus() == 200);
		String token = new JSONObject(response.getBody().toString()).getString("token");
		
		HttpHelper http = new HttpHelper();
		http.setToken(token);
		http.setBaseUrl("http://localhost:" + port);

		response = Unirest.get(api + "/user").asString();
		assertTrue(response.getStatus() == 403);

		response = Unirest.get(api + "/user").header("x-auth-token", "abcde").asString();
		assertTrue(response.getStatus() == 403);

		response = Unirest.get(api + "/user").header("x-auth-token", token).asString();
		assertTrue(response.getStatus() == 200);
		
		String moduleJsonStr = "    {\n" + 
				"        \"name\": \"Mein Testmodul\"\n" + 
				"    }\n";

		HttpResponse<JsonNode> responseJson = Unirest.post(api + "/defaultmodule").header("x-auth-token", token)
				.header("content-type", "application/json")
				.body(moduleJsonStr)
				.asJson();
		
		assertTrue(responseJson.getStatus() == 200);
		JSONObject moduleJson = responseJson.getBody().getObject();
		assertTrue(moduleJson.getJSONObject("createdBy").getInt("id") == userId);
		Integer moduleId = moduleJson.getInt("id");

		// test that created by user for intents is automatically filled
		String intentJson = "    {\n" + 
				"        \"module\": {\n" + 
				"            \"id\": " + moduleId + "\n" + 
				"        },\n" + 
				"        \"name\": \"Begruessung\",\n" + 
				"        \"description\": \"Begruessungen und andere Anreden\"\n" + 
				"    }";
		response = Unirest.post(api + "/intent").header("x-auth-token", token).header("content-type", "application/json").body(intentJson).asString();
		assertTrue(response.getStatus() == 200);

		JSONObject intentResponseJson = new JSONObject(response.getBody().toString());
		assertTrue(intentResponseJson.getJSONObject("createdBy").getInt("id") == userId);
		Integer intentId = intentResponseJson.getInt("id");

		// test is createdby user for chatbots is automatically filled in
		String chatbotJsonStr = "    {\n" + 
				"        \"name\": \"Mein chatbot\"\n" + 
				"    }\n";

		responseJson = Unirest.post(api + "/chatbot").header("x-auth-token", token)
				.header("content-type", "application/json")
				.body(chatbotJsonStr)
				.asJson();
		JSONObject chatbotResponseJson = responseJson.getBody().getObject();
		assertTrue(chatbotResponseJson.getJSONObject("createdBy").getInt("id") == userId);
		
		// test dialognodes
		String nodeJson = "    {\n" + 
				"        \"defaultModule\": {\n" + 
				"            \"id\": " + moduleId + "\n" + 
				"        },\n" + 
				"        \"parent\": null,\n" + 
				"        \"name\": \"Node Name\",\n" + 
				"        \"ordering\": 3\n" + 
				"    }\n";
		response = Unirest.post(api + "/dialog/node").header("x-auth-token", token).header("content-type", "application/json").body(nodeJson).asString();
		assertTrue(response.getStatus() == 200);
		JSONObject node1Json = new JSONObject(response.getBody().toString());
		Integer node1Id = node1Json.getInt("id");
		
		nodeJson = "    {\n" + 
				"        \"defaultModule\": {\n" + 
				"            \"id\": " + moduleId + "\n" + 
				"        },\n" + 
				"        \"parent\": {\n" + 
				"            \"id\": " + node1Id + "\n" + 
				"        },\n" + 
				"        \"name\": \"Node Name\",\n" + 
				"        \"ordering\": 3\n" + 
				"    }\n";
		response = Unirest.post(api + "/dialog/node").header("x-auth-token", token).header("content-type", "application/json").body(nodeJson).asString();
		Integer node2Id = new JSONObject(response.getBody().toString()).getInt("id");
		assertTrue(response.getStatus() == 200);

		JSONArray allNodes = Unirest.get(api + "/dialog/node").header("x-auth-token", token).header("content-type", "application/json").asJson().getBody().getArray();
		for( int i=0; i<allNodes.length(); i++) {
			JSONObject node = allNodes.getJSONObject(i);
			assertTrue(node.getJSONObject("defaultModule") != null);
		}
		
		// add conditions and responses to the dialog node
		String conditionJson = "    {\n" + 
				"        \"intent\": {\n" + 
				"            \"id\": " + intentId + "\n" + 
				"        },\n" + 
				"        \"node\": {\n" + 
				"            \"id\": " + node1Id + "\n" + 
				"        }\n"
				+ "}";

		response = http.post("/api/dialog/intentcondition").body(conditionJson).asString();
		assertTrue(response.getStatus() == 200);
		
		String answerJson = "{\n" + 
				"        \"node\": {\n" + 
				"            \"id\": " + node1Id + "\n" + 
				"        },\n" + 
				"        \"text\": \"Hallo, schön dich kennenzulernen!\",\n" + 
				"        \"ordering\": 1\n" + 
				"    }";
		response = http.post("/api/dialog/textanswer").body(answerJson).asString();
		assertTrue(response.getStatus() == 200);
		
		// update dialog nodes name only, see if children, conditions and responses are still there
		nodeJson = "{ \"name\": \"Neuer Name\"}";
		response = http.put("/api/dialog/node/" + node1Id).body(nodeJson).asString();
		assertTrue(response.getStatus() == 200);
		
		String responseStr = http.get("/api/dialog/node/" + node1Id).asString().getBody();
		JSONObject json = new JSONObject(responseStr);
		assertTrue(json.getJSONArray("dialogNodeTextAnswers").length()>0);
		assertTrue(json.getJSONArray("dialogNodeIntentConditions").length()>0);
		assertTrue(json.getJSONArray("children").length()>0);
		
		nodeJson = "{"
				+ "\"parent\": null"
				+ "}";
		response = Unirest.put(api + "/dialog/node/" + node2Id).header("x-auth-token", token).header("content-type", "application/json").body(nodeJson).asString();

		JSONArray array = Unirest.get(api + "/dialog/node").header("x-auth-token", token).header("content-type", "application/json").asJson().getBody().getArray();
		for( int i=0; i<array.length(); i++) {
			JSONObject o = array.getJSONObject(i);
			if(o.getInt("id") == node1Id) {
				assertTrue(o.getJSONArray("children").length() == 0);
			}
		}
				
		// now check this bug: there are node1, node2 and node3 and node1 is parent of node2, node2 is parent of node3.
		// when i update name of node3, then the parent / child structure gets lost
		
		nodeJson = "{"
				+ "\"parent\": {\n"
				+ "\"id\": " + node1Id + "\n"
				+ "}\n"
				+ "}";
		response = Unirest.put(api + "/dialog/node/" + node2Id).header("x-auth-token", token).header("content-type", "application/json").body(nodeJson).asString();
		assertTrue(response.getStatus() == 200);

		nodeJson = "    {\n" + 
				"        \"defaultModule\": {\n" + 
				"            \"id\": " + moduleId + "\n" + 
				"        },\n" + 
				"        \"parent\": {\n" + 
				"            \"id\": " + node2Id + "\n" + 
				"        },\n" + 
				"        \"name\": \"Node 3 Name\",\n" + 
				"        \"ordering\": 3\n" + 
				"    }\n";
		response = Unirest.post(api + "/dialog/node").header("x-auth-token", token).header("content-type", "application/json").body(nodeJson).asString();
		Integer node3Id = new JSONObject(response.getBody().toString()).getInt("id");
		assertTrue(response.getStatus() == 200);

		nodeJson = "{"
				+ "\"name\": \"test\" \n"
				+ "}";
		response = Unirest.put(api + "/dialog/node/" + node3Id).header("x-auth-token", token).header("content-type", "application/json").body(nodeJson).asString();

		array = Unirest.get(api + "/dialog/node").header("x-auth-token", token).header("content-type", "application/json").asJson().getBody().getArray();
		for( int i=0; i<array.length(); i++) {
			JSONObject o = array.getJSONObject(i);
			if(o.getInt("id") == node1Id) {
				assertTrue(o.getJSONArray("children").length() == 1);
			}
			if(o.getInt("id") == node2Id) {
				assertTrue(o.getJSONArray("children").length() == 1);
			}
		}
	}
}

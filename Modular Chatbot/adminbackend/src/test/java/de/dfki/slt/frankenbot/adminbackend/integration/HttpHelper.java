package de.dfki.slt.frankenbot.adminbackend.integration;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * Helper class to create http requests in the unit tests
 * @author jan
 *
 */
public class HttpHelper {

	String token;
	String baseUrl;
	
	public GetRequest get(String url) {
		GetRequest request = Unirest.get(baseUrl + url);
		if(token != null) {
			request.header("x-auth-token", token);
		}
		return request;
	}
	
	public HttpRequestWithBody post(String url) {
		HttpRequestWithBody request = Unirest.post(baseUrl + url).header("content-type", "application/json");
		if(token != null) {
			request.header("x-auth-token", token);
		}
		return request;
	}

	public HttpRequestWithBody put(String url) {
		HttpRequestWithBody request = Unirest.put(baseUrl + url).header("content-type", "application/json");
		if(token != null) {
			request.header("x-auth-token", token);
		}
		return request;
	}


	public HttpRequestWithBody delete(String url) {
		HttpRequestWithBody request = Unirest.delete(baseUrl + url);
		if(token != null) {
			request.header("x-auth-token", token);
		}
		return request;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	
}

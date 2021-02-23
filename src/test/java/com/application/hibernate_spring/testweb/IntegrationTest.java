package com.application.hibernate_spring.testweb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

public class IntegrationTest {
	@Test
	public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived()
			throws ClientProtocolException, IOException {

		// Given
		String name = RandomStringUtils.randomAlphabetic(8);
		HttpUriRequest request = new HttpGet("http://localhost:8081/person/getbyname/" + name);

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		// Then
		assertEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
	}
}

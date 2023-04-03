package com.sivalabs.tcdemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.CoreMatchers.is;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class GithubControllerIntegrationTCTest {

	@Container
	static MockServerContainer mockServerContainer = new MockServerContainer(
			DockerImageName.parse("mockserver/mockserver:5.15.0"));

	@DynamicPropertySource
	static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("github.api.base-url", mockServerContainer::getEndpoint);
	}

	static MockServerClient mockServerClient;

	@BeforeAll
	static void beforeAll() {
		mockServerClient = new MockServerClient(
				mockServerContainer.getHost(),
				mockServerContainer.getServerPort()
		);
	}

	@BeforeEach
	void setUp() {
		mockServerClient.reset();
	}

	@Autowired
	MockMvc mockMvc;

	@Test
	void shouldGetGithubUserProfile() throws Exception {
		String username = "sivaprasadreddy";
		mockServerClient.when(request().withMethod("GET").withPath("/users/.*"))
				.respond(response().withStatusCode(200)
						.withHeader("Content-Type", "application/json")
						.withBody(json("""
						{
							"login": "%s",
							"name": "K. Siva Prasad Reddy",
							"twitter_username": "sivalabs",
							"public_repos": 50
						}
						""".formatted(username))));

		this.mockMvc.perform(get("/api/users/{username}", username))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.login", is(username)))
				.andExpect(jsonPath("$.name", is("K. Siva Prasad Reddy")))
				.andExpect(jsonPath("$.public_repos", is(50)));

		mockServerClient.verify(request().withMethod("GET").withPath("/users/.*"),
						VerificationTimes.exactly(1));
	}

	@Test
	void shouldGetFailureResponseWhenGitHubApiFailed() throws Exception {
		String username = "sivaprasadreddy";

		mockServerClient.when(request().withMethod("GET").withPath("/users/.*"))
				.respond(response().withStatusCode(500));

		String expectedError = "Fail to fetch github profile for " + username;
		this.mockMvc.perform(get("/api/users/{username}", username))
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.message", is(expectedError)));
	}
}
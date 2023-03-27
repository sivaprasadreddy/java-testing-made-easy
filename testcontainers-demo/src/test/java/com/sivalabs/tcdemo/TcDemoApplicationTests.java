package com.sivalabs.tcdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:tc:postgresql:15.2-alpine:///db"
})
class TcDemoApplicationTests {

	@Test
	void contextLoads() {
	}

}

package com.sivalabs.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreetingServiceTest {

    GreetingService greetingService;

    @BeforeEach
    void setUp() {
        greetingService = new GreetingService();
    }

    @Test
    void shouldGreet() {
        String hello = greetingService.sayHello("Siva");
        assertEquals("Hello Siva", hello);
    }
}
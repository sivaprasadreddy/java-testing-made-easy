package com.sivalabs.jtme;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EchoServiceTest {

    EchoService echoService;

    @Before
    public void setUp() throws Exception {
        echoService = new EchoService();
    }

    @Test
    public void shouldEchoGivenName() {
        String echo = echoService.echo("Siva");
        assertEquals("Siva...Siva", echo);
    }
}
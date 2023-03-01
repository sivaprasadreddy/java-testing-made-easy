package com.sivalabs.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    PersonService personService;

    @BeforeEach
    void setUp() {
        PersonRepository repo = new PersonRepository();
        personService = new PersonService(repo);
    }

    @Test
    void shouldCreatePerson() {
        Person person = personService.create(new Person(null, "Siva", "siva@gmail.com"));
        assertNotNull(person.getId());
        assertEquals("Siva", person.getName());
        assertEquals("siva@gmail.com", person.getEmail());
    }
}
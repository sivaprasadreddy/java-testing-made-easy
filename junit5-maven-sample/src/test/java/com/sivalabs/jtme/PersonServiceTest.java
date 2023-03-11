package com.sivalabs.jtme;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    PersonService personService;

    @BeforeEach
    void setUp() {
        PersonRepository repo = new PersonRepository();
        personService = new PersonService(repo);
    }

    @AfterEach
    void tearDown() {
        personService.deleteAll();
    }

    @Nested
    class CreatePersonTests {
        @Test
        void shouldCreatePersonSuccessfully() {
            Person person = personService.create(new Person(null, "Siva", "siva@gmail.com"));
            assertNotNull(person.getId());
            assertEquals("Siva", person.getName());
            assertEquals("siva@gmail.com", person.getEmail());
        }

        @Test
        void shouldFailToCreatePersonWithExistingEmail() {
            String email = UUID.randomUUID() +"@gmail.com";
            personService.create(new Person(null, "Siva", email));

            assertThatThrownBy(()-> personService.create(new Person(null, "Siva", email)))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Person with email '"+email+"' already exists");
        }
    }

    @Nested
    class FindPersonByEmailTests {
        String email;

        @BeforeEach
        void setUp() {
            email = UUID.randomUUID() +"@gmail.com";
            personService.create(new Person(null, "Siva", email));
        }

        @Test
        void shouldGetPersonByEmailWhenExists() {
            Optional<Person> optionalPerson = personService.findByEmail(email);
            assertThat(optionalPerson).isPresent();
        }

        @Test
        void shouldGetEmptyWhenPersonByEmailNotExists() {
            Optional<Person> optionalPerson = personService.findByEmail("random@mail.com");
            assertThat(optionalPerson).isEmpty();
        }
    }
}
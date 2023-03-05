package com.sivalabs.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertThat(person.getId()).isNotNull();
        assertThat(person.getName()).isEqualTo("Siva");
        assertThat(person.getEmail()).isEqualTo("siva@gmail.com").endsWith("@gmail.com");
    }

    @Test
    void shouldThrowExceptionWhenCreatePersonWithDuplicateEmail() {
        String email = UUID.randomUUID().toString()+"@gmail.com";
        personService.create(new Person(null, "Siva", email));

        //Junit 5 assertion
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personService.create(new Person(null, "Siva", email));
        });
        assertTrue(exception.getMessage()
                .contentEquals("Person with email '"+email+"' already exists"));

        //Assertj assertion
        assertThatThrownBy(()-> {
            personService.create(new Person(null, "Siva", email));
        }).isInstanceOf(RuntimeException.class)
          .hasMessage("Person with email '"+email+"' already exists");
    }

    @Test
    void showAssertjAwesomeness() {
        String name = "Siva Prasad Reddy";
        int age = 35;
        assertThat(name).startsWith("Siv");
        assertThat(name).containsIgnoringCase("pra");
        assertThat(age).isGreaterThan(30);

        /* ================================================ */
        Person person1 = new Person(1L, "Siva", "siva@gmail.com");
        Person person2 = new Person(2L, "Prasad", "prasad@gmail.com");
        Person person3 = new Person(1L, "Siva", "siva@gmail.com");

        assertThat(person1).usingRecursiveComparison().isEqualTo(person3);

        Person person4 = new Person(null, "Siva", "sivalabs@gmail.com");
        Person person5 = new Person(null, "Siva", "sivalabs@gmail.com");
        assertThat(person4)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(person5);

        assertThat(person4)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "email")
                .isEqualTo(person5);

        /* ================================================ */
        List<Person> personList = List.of(person1, person2, person4);
        Person person = new Person(2L, "Prasad", "prasad@gmail.com");

        assertThat(personList).contains(person);

        assertThat(person)
                .usingRecursiveComparison()
                .comparingOnlyFields("id")
                .isIn(personList);

    }
}
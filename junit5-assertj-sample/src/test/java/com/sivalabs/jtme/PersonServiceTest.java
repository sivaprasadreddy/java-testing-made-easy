package com.sivalabs.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(person.getEmail()).isEqualTo("siva@gmail.com");
    }

    @Test
    void showAssertjAwesomeness() {
        String name = "Siva Prasad Reddy";
        int age = 35;
        assertThat(name).startsWith("Siv");
        assertThat(name).containsIgnoringCase("pra");
        assertThat(age).isGreaterThan(30);

        Person person1 = new Person(1L, "Siva", "siva@gmail.com");
        Person person2 = new Person(2L, "Prasad", "prasad@gmail.com");
        Person person3 = new Person(3L, "Siva", "sivalabs@gmail.com");

        List<Person> personList = List.of(person1, person2, person3);
        Person person = new Person(2L, "Prasad", "prasad@gmail.com");

        assertThat(personList).extracting("id").contains(2L, 3L);
        assertThat(personList).extracting("id").containsAll(List.of(1L, 2L, 3L));
        //using equals() and hashCode()
        //assertThat(personList).contains(person);

        assertThat(person).usingRecursiveComparison().isEqualTo(person2);

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
    }
}
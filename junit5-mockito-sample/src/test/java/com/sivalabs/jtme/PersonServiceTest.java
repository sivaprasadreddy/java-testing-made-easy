package com.sivalabs.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonRepository repo;

    @InjectMocks
    PersonService personService;

    // Without using @ExtendWith(MockitoExtension.class)
    /*
    @BeforeEach
    void setUp() {
        repo = Mockito.mock(PersonRepository.class);
        personService = new PersonService(repo);
    }
    */

    @Test
    void loginSuccess() {
        //Arrange
        Person person = new Person(1L, "Siva", "siva@gmail.com", "siva123");
        when(repo.findByEmailAndPassword(eq("siva@gmail.com"), anyString()))
                .thenReturn(Optional.of(person));
        //Act
        String token = personService.login("siva@gmail.com", "siva123");

        //Assert
        assertThat(token).isNotNull();
    }

    @Test
    void loginFailure() {
        when(repo.findByEmailAndPassword("siva@gmail.com", "siva123"))
                .thenReturn(Optional.empty());

        String token = personService.login("siva@gmail.com", "siva123");

        assertThat(token).isNull();
    }

    @Test
    void findByEmail() {
        Person person = new Person(1L, "Siva", "siva@gmail.com", "siva123");
        when(repo.findByEmail("siva@gmail.com")).thenReturn(Optional.of(person));

        Optional<Person> optionalPerson = personService.findByEmail("siva@gmail.com");

        assertThat(optionalPerson).isPresent();
        assertThat(optionalPerson.get().getName()).isEqualTo("Siva");
        assertThat(optionalPerson.get().getEmail()).isEqualTo("siva@gmail.com");
    }

    @Test
    void shouldCreatePersonSuccessfully() {
        when(repo.findByEmail("siva@gmail.com")).thenReturn(Optional.empty());
        when(repo.create(any(Person.class))).thenAnswer(answer -> answer.getArgument(0));

        Person person = personService.create("Siva", "SIVA@gmail.com", "siva123");

        assertEquals("Siva", person.getName());
        assertEquals("siva@gmail.com", person.getEmail());

        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(repo).create(argumentCaptor.capture());
        Person value = argumentCaptor.getValue();
        assertEquals("Siva", value.getName());
        assertEquals("siva@gmail.com", value.getEmail());
    }

    @Test
    void updatePerson() {
        Person person = new Person(1L, "Siva", "siva@gmail.com", "siva123");

        doNothing().when(repo).update(any(Person.class));
        //doThrow(new RuntimeException("Invalid email")).when(repo).update(any(Person.class));

        personService.update(person);

        verify(repo).update(any(Person.class));
        //verify(repo, times(1)).update(any(Person.class));
        //verify(repo, atMostOnce()).update(any(Person.class));
        //verify(repo, atLeastOnce()).update(any(Person.class));
    }
}
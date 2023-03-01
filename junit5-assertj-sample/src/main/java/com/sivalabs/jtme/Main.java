package com.sivalabs.jtme;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        PersonRepository repo = new PersonRepository();
        PersonService personService = new PersonService(repo);

        personService.create(new Person(null, "Siva", "siva@gmail.com"));
        personService.create(new Person(null, "Prasad", "prasad@gmail.com"));

        List<Person> people = personService.findAll();
        people.forEach(System.out::println);

        Optional<Person> byEmail = personService.findByEmail("siva@gmail.com");
        System.out.println(byEmail);
    }
}
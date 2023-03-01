package com.sivalabs.jtme;

import java.util.List;
import java.util.Optional;

public class PersonService {
    private final PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public Person create(Person person) {
        checkNotEmpty(person.getName(), "Name is required");
        checkNotEmpty(person.getName(), "Email is required");
        Optional<Person> byEmail = repo.findByEmail(person.getEmail());
        if(byEmail.isPresent()) {
            throw new RuntimeException("Person with email '"+person.getEmail()+"' already exists");
        }
        repo.create(person);
        return person;
    }

    public Optional<Person> findById(Long id) {
        return repo.findById(id);
    }

    public List<Person> findAll() {
        return repo.findAll();
    }

    public Optional<Person> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public Person update(Person person) {
        checkNotEmpty(person.getName(), "Name is required");
        checkNotEmpty(person.getName(), "Email is required");
        return repo.update(person);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private void checkNotEmpty(String value, String message) {
        if(value == null || value.trim().isEmpty()) {
            throw new RuntimeException(message);
        }
    }
}

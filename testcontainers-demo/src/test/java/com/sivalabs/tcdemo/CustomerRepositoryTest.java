package com.sivalabs.tcdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:postgresql:15.2-alpine:///db"
})
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
       customerRepository.deleteAll();
    }

    @Test
    void shouldGetAllCustomers() {
        List<Customer> customers = List.of(
                new Customer(null, "John", "john@mail.com", "john"),
                new Customer(null, "Dennis", "dennis@mail.com", "dennis")
        );
        customerRepository.saveAll(customers);

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(2);
    }

    @Test
    void shouldLoginSuccessfully() {
        Customer customer = new Customer(null, "John", "john@mail.com", "john");
        customerRepository.save(customer);

        Optional<Customer> optionalCustomer = customerRepository.findByEmailAndPassword("john@mail.com", "john");
        assertThat(optionalCustomer).isPresent();
    }
}
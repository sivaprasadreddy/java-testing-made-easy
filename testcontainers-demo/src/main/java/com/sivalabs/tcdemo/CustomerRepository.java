package com.sivalabs.tcdemo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmailAndPassword(String email, String password);
}
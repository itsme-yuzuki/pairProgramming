package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	//SELECT email from customers
	Optional<Customer> findByEmail(String email); 
	
	//SELECT name FROM customers WHERE email=? AND password=?
	Optional<Customer> findByEmailAndPassword(String email, String password);
}

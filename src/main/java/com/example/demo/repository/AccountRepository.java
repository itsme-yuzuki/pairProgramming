package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Customer;
import com.example.demo.model.Account;

public interface AccountRepository extends JpaRepository<Customer, Integer> {

	//SELECT name FROM customers WHERE email=? AND password=?
	Optional<Account> findByIdAndPassword(integer id, String password);
}

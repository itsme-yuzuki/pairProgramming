package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	//SELECT name FROM customers WHERE email=? AND password=?
	Optional<Account> findByIdAndPassword(Integer id, String password);
	
	Optional<Account> findByIdAndEmail(Integer id, String email);
	
}

package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Account;
import com.example.demo.model.PasswordResetGenerator;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	//SELECT name FROM customers WHERE email=? AND password=?
	Optional<Account> findByAccountIdAndPassword(Integer accountId, String password);
	
	Optional<Account> findByAccountIdAndEmail(Integer accountId, String email);
	
	List<Account>findAllByOrderByAccountId();

	void save(Integer accountId, PasswordResetGenerator pwdGen);

}

package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	//SELECT * FROM account WHERE email=? AND password=?
	Optional<Account> findByAccountIdAndPassword(Integer accountId, String password);
	
	//SELECT * FROM account WHERE accountId=? AND email=?
	Optional<Account> findByAccountIdAndEmail(Integer accountId, String email);
	
	//SELECT * FROM account ORDER BY accountId
	List<Account>findAllByOrderByAccountId();
	
	//UPDATE password WHERE accountId=?
//	public void save(Integer accountId, String randomString);

}

	
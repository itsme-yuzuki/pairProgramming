package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	@Autowired
	HttpSession session;
	
	@Autowired
	Account account;
	
	@Autowired
	AccountRepository accountRepository;
	
	//getでアカウント一覧を表示
	@GetMapping("/account")
	public String index(
		@RequestParam(name= "admin", defaultValue= "")String admin,
		Model model) {
		List<Account> users = accountRepository.findAll();
		model.addAttribute("users", users);
			return "admin";
		}

	//postでアカウントの新規追加
	@PostMapping("/admin/add")
	public String addAccount(
			@RequestParam(name= "name") String name,
			@RequestParam(name= "email", defaultValue="") String email,
			@RequestParam(name= "password", defaultValue= "") String password,
			@RequestParam(name= "authorise_id", defaultValue="") Integer authoriseId,
			Model model
			) {
		Account account= new Account(name, email, password, authoriseId);
		model.addAttribute("account", account);
			
		return "admin";
	}
	
}

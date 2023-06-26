package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	@Autowired
	HttpSession session;
	
	@Autowired
	AccountRepository accountRepository;
	
	//getでアカウント一覧を表示
	@GetMapping("/account")
	public String index(
		@RequestParam(name= "admin", defaultValue= "")String admin,
		Model model) {
		List<User> users = accountRepository.findAll();
		model.addAttribute("users", users);
			return "admin";
		}
	}

	//postでアカウントの新規追加
	@PostMapping("/admin")
	public String addAccount(
			@RequestParam(name= "name") String name,
			@RequestParam(name= "email" default) String email,
			@RequestParam(name= "password") String password,
			@RequestParam(name= "authorise_id") String authoriseId,
			Model model
			) {
		
	

	
}

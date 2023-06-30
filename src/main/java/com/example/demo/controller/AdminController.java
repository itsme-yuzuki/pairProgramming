package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@GetMapping("/admin")
	public String index(
		Model model) {
		List<Account> accounts = accountRepository.findAllByOrderByAccountId();
		model.addAttribute("accounts", accounts);
			return "admin";
		}

	//アカウント新規追加画面への遷移
	@GetMapping("/admin/add")
	public String accountAdding() {
		return "addAccount";
	}
	
	//アカウント新規追加
	@PostMapping("/admin/add")
	public String addAccount(
			@RequestParam(name= "name") String name,
			@RequestParam(name= "email", defaultValue="") String email,
			@RequestParam(name= "password", defaultValue= "") String password,
			@RequestParam(name= "authorise_id", defaultValue="") Integer authoriseId,
			Model model
			) {
		Account account= new Account(name, email, password, authoriseId);
		accountRepository.save(account);
		
		return "redirect:/admin";
	}
	
	//アカウント編集
	@GetMapping("/admin/{id}/edit")
	public String editAccount(
			@PathVariable("id") Integer id,
			Model model
			) {
		account = accountRepository.findByAccountId(id).get();
		model.addAttribute("account", account);
			
		return "adminEdit";
	}
	
	//アカウント編集登録
	@PostMapping("/admin/edit")
	public String editAccount(
			@RequestParam("id") Integer id,
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("authoriseId") Integer authoriseId,
			Model model
			) {
		Account account= new Account(id, name, email, password, authoriseId);
		accountRepository.save(account);
		
		return "redirect:/admin";
	}
	
	//アカウント削除
	@PostMapping("/admin/{id}/delete")
	public String deleteAccount(
			@PathVariable("id")Integer id,
			Model model
			) {
		accountRepository.deleteById(id);
		
		return "redirect:/account";
	}
	
}

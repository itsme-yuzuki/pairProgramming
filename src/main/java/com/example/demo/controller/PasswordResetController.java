package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Account;
import com.example.demo.model.PasswordResetGenerator;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PasswordResetController {

	@Autowired
	HttpSession session;
	
	@Autowired
	User user;
	
	@Autowired
	PasswordResetGenerator passwordResetGenerator;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	Account account;

	//仮パスワードを生成
	@PostMapping("/reset")
	public String reset() {
		PasswordResetGenerator.getRandomString(0);
		
	//データベースに仮パスワードを登録
		accountRepository.save();
	
	//登録した仮パスワードをメールアドレスに送信
		
		
	//ログイン画面にリダイレクト
		return "redirect:/login";
	
	}
}

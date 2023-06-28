package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@PostMapping("/passwordReset")
	public String reset(
			@RequestParam("accountId") Integer accountId
			) {
		int i= 12;
		PasswordResetGenerator.getRandomString(i);
	//データベースに仮パスワードを登録
		accountRepository.save(accountId, PasswordResetGenerator.getRandomString(i));

	
	//登録した仮パスワードをメールアドレスに送信
		
		
	//ログイン画面にリダイレクト
		return "redirect:/login";
	
	}
}

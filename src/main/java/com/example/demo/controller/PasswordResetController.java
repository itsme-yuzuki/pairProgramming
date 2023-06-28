package com.example.demo.controller;

import java.util.Optional;

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

	@Autowired
	MailController mailController;

	//仮パスワードを生成
	@PostMapping("/reset")
	public String reset(
			@RequestParam(name = "accountId") Integer accountId,
			@RequestParam("email") String email) {
		int i = 12;
		String password = PasswordResetGenerator.getRandomString(i);

		//データベースに仮パスワードを登録
		Optional<Account> record = accountRepository.findById(accountId);
		account = record.get();
		account.setPassword(password);
		accountRepository.save(account);

		mailController.send(email, password);
		//登録した仮パスワードをメールアドレスに送信

		//ログイン画面にリダイレクト
		return "redirect:/login";

	}

}

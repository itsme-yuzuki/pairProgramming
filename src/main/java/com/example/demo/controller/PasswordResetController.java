package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
			@RequestParam(name = "accountId") String accountId,
			@RequestParam("email") String email,
			Model model) {

		Integer id = null;

		try {
			id = Integer.parseInt(accountId);
		} catch (Exception e) {
			model.addAttribute("message", "社員番号は数字で入力してください");
			return "passwordReset";
		}

		//社員番号があっているかどうかチェック
		Optional<Account> record = accountRepository.findByAccountIdAndEmail(id, email);

		if (record.isEmpty()) {			
			return "redirect:/passwordReset?error=wrong";	
		} else if (record.isEmpty() == false) {
			//データベースに仮パスワードを登録
			int i = 12;
			String password = PasswordResetGenerator.getRandomString(i);
			account = record.get();
			account.setPassword(password);
			accountRepository.save(account);
			mailController.send(email, password);
		}

		//データベースに仮パスワードを登録

		//登録した仮パスワードをメールアドレスに送信

		//ログイン画面にリダイレクト
		return "redirect:/login?a=sent";

	}

}

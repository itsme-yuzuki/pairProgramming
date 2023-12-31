package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.LeaveStatus;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LeaveStatusRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController2 {

	@Autowired
	HttpSession session;

	@Autowired
	User user;

	@Autowired
	Account account;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	LeaveStatus leaveStatus;

	@Autowired
	LeaveStatusRepository leaveStatusRepository;

	@GetMapping("/accountDetail")
	public String accountDetail(Model model) {

		account = null;

		Optional<Account> record = accountRepository.findById(user.getAccountId());
		//		List<AuthoriseName> authoriseName = authoriseNameRepository.findAll();

		if (record.isEmpty() == false) {
			account = record.get();
		}

		//		model.addAttribute("authoriseName", authoriseName);
		model.addAttribute("account", account);

		return "accountDetail";
	}

	//パスワード変更作業
	@PostMapping("/accountDetail")
	public String accountDetail(
			@RequestParam(name = "oldPassword") String oldPassword,
			@RequestParam(name = "newPassword", defaultValue = "") String newPassword,
			@RequestParam(name = "comPassword", defaultValue = "") String comPassword,
			Model model) {
		Optional<Account> record = accountRepository.findById(user.getAccountId());
		//		Optional<Account> record = accountRepository.findById(1);

		account = record.get();

		List<String> errormessage = new ArrayList<String>();

		if (account.getPassword().equals(oldPassword) == false) {
			errormessage.add("登録中のパスワードと一致しません");
		}

		if (newPassword.equals("") || newPassword.length() < 10) {
			errormessage.add("新しいパスワードは最低10桁で設定してください");
		}

		if (comPassword.equals(newPassword) == false) {
			errormessage.add("確認用のパスワードが間違っています");
		}

		if (errormessage.size() > 0) {
			model.addAttribute("errormessage", errormessage);
			return accountDetail(model);
		}

		account.setPassword(newPassword);

		accountRepository.save(account);

		model.addAttribute("message", "パスワードの変更が成功しました");

		return accountDetail(model);
	}

	@GetMapping("/supervisor")
	public String index(Model model) {
		List<Account> accounts = accountRepository.findByAuthoriseIdLessThanOrderByAccountId(2);

		account = accountRepository.findByAccountId(user.getAccountId()).get();

		model.addAttribute("account", account);
		model.addAttribute("accounts", accounts);

		return "supervisor";
	}

	@PostMapping("/supervisor/{id}")
	public String set(
			@PathVariable("id") Integer accountId,
			Model model) {

		account = accountRepository.findByAccountId(user.getAccountId()).get();

		account.setAuthoriserId(accountId);

		accountRepository.save(account);

		model.addAttribute("message", "登録完了しました");

		return index(model);
	}
}

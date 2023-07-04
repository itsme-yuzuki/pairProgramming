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
public class AccountController {

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

	//	 ログイン画面を表示
	@GetMapping({ "login", "/", "logout" })
	public String index(
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		// セッション情報を全てクリアする
		session.invalidate();

		// エラーパラメータのチェック
		if (password.equals("forget")) {
			return "passwordReset";
		} else if (password.equals("reset")) {
			model.addAttribute("message", "パスワード送信済み");
		}

		return "login";
	}

	// ログインを実行
	@PostMapping("/login")
	public String login(
			@RequestParam("accountId") Integer accountId,
			@RequestParam("password") String password,
			Model model) {

		account = null;

		Optional<Account> record = accountRepository.findByAccountIdAndPassword(accountId, password);

		if (record.isEmpty() == false) {
			account = record.get();
		}

		if (account == null) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			return "login";
		}

		leaveStatus = leaveStatusRepository.findById(accountId).get();

		// セッション管理されたアカウント情報に名前をセット
		user.setLeaveRemain(leaveStatus.getLeaveRemain());
		user.setName(account.getName());
		user.setAccountId(account.getAccountId());
		user.setAuthorise(account.getAuthoriseId());

		return "redirect:/index";
	}

	//パスワードリセット作業
	@PostMapping("/passwordReset")
	public String passwordReset(
			@RequestParam("accountId") Integer accountId,
			@RequestParam("email") String email,
			Model model) {

		Optional<Account> emailCheck = accountRepository.findByAccountIdAndEmail(accountId, email);

		if (emailCheck.isEmpty()) {
			model.addAttribute("message", "社員番号とメールアドレスが不一致");
			return "passwordReset";
		}

		return "";
	}

	@GetMapping("/accountDetail")
	public String accountDetail(Model model) {

		account = null;

		Optional<Account> record = accountRepository.findById(user.getAccountId());
		//		Optional<Account> record = accountRepository.findById(1);

		if (record.isEmpty() == false) {
			account = record.get();
		}

		model.addAttribute("account", account);

		return "accountDetail";
	}

	//パスワード変更作業
	@PostMapping("/accountDetail")
	public String accountDetail(
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			Model model) {
		Optional<Account> record = accountRepository.findById(user.getAccountId());
		//		Optional<Account> record = accountRepository.findById(1);

		account = record.get();

		List<String> errormessage = new ArrayList<String>();

		if (account.getPassword().equals(oldPassword) == false) {
			errormessage.add("パスワードが不一致");
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
		
		model.addAttribute("accounts", accounts);

		return "supervisor";
	}

	@PostMapping("/supervisor/{id}")
	public String set(
			@PathVariable("id") Integer accountId,
			Model model) {
		account.setAuthoriserId(accountId);
		accountRepository.save(account);

		model.addAttribute("message", "登録完了しました");

		return index(model);
	}
}

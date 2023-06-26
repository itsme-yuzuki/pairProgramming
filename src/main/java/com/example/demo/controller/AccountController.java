package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	User user;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	Account account;

	// ログイン画面を表示
	@GetMapping({ "/login", "/logout" })
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
			@RequestParam("id") Integer id,
			@RequestParam("password") String password,
			Model model) {

		//DBからアドレスとパスワードを基に名前と顧客IDを取得する？
		account = null;

		Optional<Account> record = accountRepository.findByIdAndPassword(id, password);

		if (record.isEmpty() == false) {
			account = record.get();
		}

		if (account == null) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			return "login";
		}

		// セッション管理されたアカウント情報に名前をセット
		user.setName(account.getName());
		user.setId(account.getId());

		return "redirect:/items";
	}

	@PostMapping("/passwordReset")
	public String passwordReset(
			@RequestParam("id") Integer id,
			@RequestParam("email") String email,
			Model model) {

		Optional<Account> emailCheck = accountRepository.findByIdAndEmail(id, email);

		if (emailCheck.isEmpty()) {
			model.addAttribute("message", "社員番号とメールアドレスが不一致");
			return "passwordReset";
		}

		return "";

	}

	@GetMapping("accountDetail")
	public String accountDetail(Model model) {

		Optional<Account> account = accountRepository.findById(user.getId());

		model.addAttribute("account", account);

		return "accountDetail";
	}

	@PostMapping("accountDetail")
	public String accountDetail(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			Model model) {
		Optional<Account> passwordCheck = accountRepository.findByIdAndPassword(user.getId(), oldPassword);

		List<String> errorMessage = new ArrayList<String>();

		if (passwordCheck.isEmpty()) {
			errorMessage.add("パスワードが不一致");
		}
		if (errorMessage.size() > 0) {
			model.addAttribute("message",errorMessage);
			return "accountDetail";
		}

		account = new Account(user.getId(), newPassword);
		
		accountRepository.save(account);

		model.addAttribute("message", "パスワードの変更が成功しました");
		
		return "/accountDetail";
	}
}

package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	@GetMapping({ "/login", "/", "/logout" })
	public String index(
			@RequestParam(name = "a", defaultValue = "") String a,
			Model model) {
		// セッション情報を全てクリアする
		session.invalidate();

		if (a.equals("notLoggedIn")) {
			model.addAttribute("errorMessage", "ログインしてください");
		} else if (a.equals("sent")) {
			model.addAttribute("message", "仮パスワード送信いたしました");
		}

		// エラーパラメータのチェック
		return "login";
	}

	// ログインを実行
	@PostMapping("/login")
	public String login(
			@RequestParam(name = "accountId", defaultValue = "") String accountId,
			@RequestParam("password") String password,
			Model model) {
		Integer id;
		try {
			id = Integer.parseInt(accountId);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "社員番号は数字で入力してください");
			return "login";
		}

		account = null;

		Optional<Account> record = accountRepository.findByAccountIdAndPassword(id, password);

		if (record.isEmpty() == false) {
			account = record.get();
		}

		if (account == null) {
			model.addAttribute("errorMessage", "メールアドレスとパスワードが一致しませんでした");
			return "login";
		}

		leaveStatus = leaveStatusRepository.findById(id).get();

		// セッション管理されたアカウント情報に名前をセット
		user.setLeaveRemain(leaveStatus.getLeaveRemain());
		user.setName(account.getName());
		user.setAccountId(account.getAccountId());
		user.setAuthorise(account.getAuthoriseId());

		return "redirect:/index";
	}

	//	//パスワードリセット作業
	@GetMapping("/passwordReset")
	public String passwordReset(@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {

		if (error.equals("wrong")) {
			model.addAttribute("message", "メールアドレスが一致しません");
		}

		return "passwordReset";
	}

	@GetMapping({ "admin/login", "admin/logout" })
	public String adminIndex(
			@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {

		if (error.equals("notLoggedIn")) {
			model.addAttribute("message", "ログインしてください");
		}
		// セッション情報を全てクリアする
		session.invalidate();

		return "adminLogin";
	}

	// ログインを実行
	@PostMapping("/admin/login")
	public String adminLogin(
			@RequestParam(name = "accountId", defaultValue = "") String accountId,
			@RequestParam("password") String password,
			Model model) {

		Integer id;
		try {
			id = Integer.parseInt(accountId);
		} catch (Exception e) {
			model.addAttribute("message", "社員番号は数字で入力してください");
			return "adminLogin";
		}

		account = null;

		Optional<Account> record = accountRepository.findByAccountIdAndPassword(id, password);

		if (record.isEmpty() == false && record.get().getAuthoriseId() == 0) {
			account = record.get();
		}

		if (record.isEmpty() == false && record.get().getAuthoriseId() != 0) {
			model.addAttribute("message", "管理者権限がありません");
			return "adminLogin";
		}

		if (account == null) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			return "adminLogin";
		}

		leaveStatus = leaveStatusRepository.findById(id).get();

		// セッション管理されたアカウント情報に名前をセット
		user.setLeaveRemain(leaveStatus.getLeaveRemain());
		user.setName(account.getName());
		user.setAccountId(account.getAccountId());
		user.setAuthorise(account.getAuthoriseId());

		return "redirect:/admin";
	}

}

package com.example.demo.controller;

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
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	@Autowired
	HttpSession session;

	@Autowired
	Account account;

	@Autowired
	User user;

	@Autowired
	AccountRepository accountRepository;

	//	 ログイン画面を表示
	@GetMapping({ "admin/login", "admin/logout" })
	public String index(
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		// セッション情報を全てクリアする
		session.invalidate();

		return "adminLogin";
	}

	// ログインを実行
	@PostMapping("/admin/login")
	public String login(
			@RequestParam(name = "accountId", defaultValue = "") String accountId,
			@RequestParam("password") String password,
			Model model) {

		Integer id;
		try {
			id = Integer.parseInt(accountId);
		} catch (Exception e) {
			model.addAttribute("message", "社員番号は数字で入力してください");
			return "login";
		}

		account = null;

		Optional<Account> record = accountRepository.findByAccountIdAndPassword(id, password);

		if (record.isEmpty() == false && record.get().getAuthoriseId() == 0) {
			account = record.get();
		}

		if (record.isEmpty() == false && record.get().getAuthoriseId() != 0) {
			model.addAttribute("message", "管理者権限がありません");
			return "login";
		}

		if (account == null) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			return "login";
		}

		//		leaveStatus = leaveStatusRepository.findById(accountId).get();

		// セッション管理されたアカウント情報に名前をセット
		//		user.setLeaveRemain(leaveStatus.getLeaveRemain());
		user.setName(account.getName());
		user.setAccountId(account.getAccountId());
		user.setAuthorise(account.getAuthoriseId());

		return "redirect:/admin";
	}

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
			@RequestParam(name = "name") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "authorise_id", defaultValue = "") Integer authoriseId,
			Model model) {
		Account account = new Account(name, email, password, authoriseId);
		accountRepository.save(account);

		return "redirect:/admin";
	}

	//アカウント編集
	@GetMapping("/admin/{id}/edit")
	public String editAccount(
			@PathVariable("id") Integer id,
			Model model) {
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
			Model model) {
		Account account = new Account(id, name, email, password, authoriseId);
		accountRepository.save(account);

		return "redirect:/admin";
	}

	//アカウント削除
	@PostMapping("/admin/{id}/delete")
	public String deleteAccount(
			@PathVariable("id") Integer id,
			Model model) {
		accountRepository.deleteById(id);

		return "redirect:/account";
	}

}

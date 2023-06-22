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

import com.example.demo.entity.Customer;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	Account account;
	
	@Autowired
	CustomerRepository customerRepository;

	// ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index(
			@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {
		// セッション情報を全てクリアする
		session.invalidate();
		
		// エラーパラメータのチェック
		if (error.equals("notLoggedIn")) {
			model.addAttribute("message", "ログインしてください");
		}

		return "login";
	}

	// ログインを実行
	@PostMapping("/login")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {
		
		//DBからアドレスとパスワードを基に名前と顧客IDを取得する？
		Customer customer = null;
		
		Optional<Customer> record = customerRepository.findByEmailAndPassword(email, password);
		
		if (record.isEmpty() == false) {
			customer = record.get();
		}
		
		if(customer == null){
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			return "login";
		}
		
		// セッション管理されたアカウント情報に名前をセット
		account.setName(customer.getName());
		account.setId(customer.getId());
				
		
		return "redirect:/items";
	}
	
	@GetMapping("/account")
	public String create() {
		
		return "accountForm";
		
	}
	
	@PostMapping("/account")
	public String store(
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("tel") String tel,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model m) {
		
		Optional<Customer> emailList = customerRepository.findByEmail(email);
		
		List<String> error= new ArrayList<>();
		
		if(name == "") {
			error.add("名前を入力してください");
		}
		if(address == "") {
			error.add("住所を入力してください");
		}
		if(tel == "") {
			error.add("電話番号を入力してください");
		}
		if(email== "") {
			error.add("メールアドレスを入力してください");
		}
		if(emailList.isPresent()){
			error.add("既に登録されているメールアドレスです");
		}
		if (password == "") {
			error.add("パスワードは必須です");
		}
		if (error.size()> 0){
			m.addAttribute("error",error);
			m.addAttribute("name",name);
			m.addAttribute("address", address);
			m.addAttribute("tel",tel);
			m.addAttribute("email",email);
			return "accountForm";
		}

		Customer customer = new Customer(name, address, tel, email, password);
		customerRepository.save(customer);
		
		return "redirect:/login";
		
	}
}

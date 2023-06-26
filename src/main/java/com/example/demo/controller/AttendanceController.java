package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;


	@Autowired
	HttpSession session;

	@Autowired
	Account account;

	@Autowired
	AccountRepository accountRepository;

	// ログイン画面を表示
	@GetMapping("index")
	public String index(
			@RequestParam(name = "page", defaultValue = "") String page,
			Model model) {

		// エラーパラメータのチェック
		if (page.equals("勤怠管理")) {
			return "kintai";
		} else if (page.equals("休暇管理")) {
			return "kyuuka_kannri";
		}
		return "attendaceMenu";
	}


}
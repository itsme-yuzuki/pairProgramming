package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.Attendance;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AttendanceRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AttendanceController {

	@Autowired
	HttpSession session;

	@Autowired
	User user;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	Account account;

	@Autowired
	AttendanceRepository attendanceReposity;

	@Autowired
	Attendance attendance;

	//	 ログイン画面を表示
	@GetMapping("/attendance")
	public String attendance(@RequestParam(name = "status") Integer status,
			Model model) {

		Integer telework = 0;

		LocalDate dateNow = LocalDate.now();

		LocalTime timeNow = LocalTime.now();

		LocalTime time1 = LocalTime.of(9, 00);

		LocalTime time2 = LocalTime.of(17, 30);

		Integer attendanceStatus = null;

		if (status == 1) {
			if (timeNow.isBefore(time1)) {
				//	telework=0;
				attendanceStatus = 1;
			} else if (timeNow.isAfter(time1)) {
				//	telework=0;
				attendanceStatus = 3;
			}
		}
		if (status == 2) {
			if (timeNow.isAfter(time2)) {
				//	telework=0;
				attendanceStatus = 2;
			} else if (timeNow.isBefore(time2)) {
				//	telework=0;
				attendanceStatus = 4;
			}
		}
		if (status == 3) {
			if (timeNow.isBefore(time1)) {
				telework = 1;
				attendanceStatus = 1;
			} else if (timeNow.isAfter(time1)) {
				telework = 1;
				attendanceStatus = 3;
			}
		}
		if (status == 4) {
			if (timeNow.isAfter(time2)) {
				telework = 1;
				attendanceStatus = 2;
			} else if (timeNow.isBefore(time2)) {
				telework = 1;
				attendanceStatus = 4;
			}
		}

		System.err.println(timeNow);
		String time = timeNow.toString();
		time = time.substring(0, 8);

		attendance = new Attendance(user.getAccountId(), dateNow, time, attendanceStatus, telework);

		attendanceReposity.save(attendance);

		return "homePage";
	}

	@GetMapping("/index")
	public String index(
			@RequestParam(name = "menu", defaultValue = "") Integer menu,
			Model model) {
		//		switch (menu) {
		//		case 1:
		//			return "";
		//		case 2:
		//			return "";
		//		case 3:
		//			return "";
		//		case 4:
		//			return "";
		//		case 5:
		//			return "";
		//		case 6:
		//			return "";
		//		}
		return "homePage";
	}
}

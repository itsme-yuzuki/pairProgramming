package com.example.demo.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
				attendanceStatus = 1;
			} else if (timeNow.isAfter(time1)) {
				attendanceStatus = 3;
			}
		}
		if (status == 2) {
			if (timeNow.isAfter(time2)) {
				attendanceStatus = 2;
			} else if (timeNow.isBefore(time2)) {
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

		String time = timeNow.toString();

		time = time.substring(0, 8);

		if (status == 1 || status == 3) {
			Optional<Attendance> record = attendanceReposity.findByDate(dateNow);
			if(record.isEmpty() == false) {
				model.addAttribute("message", "出勤が二重しています。修正をしてください");
				return "homePage";
			}
			model.addAttribute("message", "出勤しました");
			attendance = new Attendance(user.getAccountId(), dateNow, time, null, attendanceStatus, telework);
		} else if (status == 2 || status == 4) {
			Optional<Attendance> record = attendanceReposity.findByDate(dateNow);
			if (record.isEmpty()) {
				model.addAttribute("message", "出勤記録がありません。修正をしてください");
				return "homePage";
			}
			if(record.isEmpty() == false) {
				attendance =  record.get();
				if(attendance.getLeftTime() != null) {
					model.addAttribute("message", "退勤が二重しています。修正をしてください");
					return "homePage";
				}
			}
			model.addAttribute("message", "退勤しました");
			attendance = record.get();
			attendance.setLeftTime(time);
		}
		attendanceReposity.save(attendance);

		return "homePage";
	}

	@GetMapping("/index")
	public String index(
			@RequestParam(name = "menu", defaultValue = "0") Integer menu,
			Model model) {

		int accountId = user.getAccountId();

		
		
		switch (menu) {
		case 1:
			List<Attendance> attendance = attendanceReposity.findByAccountIdOrderByDate(accountId);
			model.addAttribute("attendance", attendance);
			return "attendance";
		case 2:
			return "attendanceEdit";
		case 3:
			return "pending";
		case 4:
			return "supervisor";
		case 5:
			return "leave";
		case 6:
			return "leaveRequest";
		case 7:
			return "alternate";
		case 8:
			return "alternateRequest";
		case 9:
			return "editPassword";
		}
		return "homePage";
	}
	
	@PostMapping("/edit/attendance")
	public String editAttendance(
			@RequestParam("date") Date date,
			@RequestParam("arrivingTime")Time arrivingtime,
			@RequestParam("leftTime")Time leftTime,
			@RequestParam("attendaceId")Integer attendaceId,
			@RequestParam("telework")Integer telework,
			Model model
			) {
		
		return "redirect:/attendanceEdit";
	}
}

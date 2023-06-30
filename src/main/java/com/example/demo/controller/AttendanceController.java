package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.AttendanceType;
import com.example.demo.entity.Date2023;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.AttendanceTypeRepository;
import com.example.demo.repository.Date2023Repository;

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
	AttendanceRepository attendanceRepository;

	@Autowired
	Attendance attendance;

	@Autowired
	Date2023 date2023;

	@Autowired
	Date2023Repository date2023Repository;

	@Autowired
	AttendanceTypeRepository attendanceTypeRepository;

	@Autowired
	AttendanceType attendanceType;

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

			Optional<Attendance> record = attendanceRepository.findByDateAndAccountId(dateNow.toString(), user.getAccountId());

			if (record.isEmpty() == false) {
				model.addAttribute("message", "出勤が二重しています。修正をしてください");
				return "homePage";
			}
			model.addAttribute("message", "出勤しました");
			attendance = new Attendance(user.getAccountId(), dateNow.toString(), time, null, attendanceStatus, null,
					telework);
		} else if (status == 2 || status == 4) {

			Optional<Attendance> record = attendanceRepository.findByDateAndAccountId(dateNow.toString(),
					user.getAccountId());

			if (record.isEmpty()) {
				model.addAttribute("message", "出勤記録がありません。修正をしてください");
				return "homePage";
			}
			if (record.isEmpty() == false) {
				attendance = record.get();
				if (attendance.getLeftTime() != null) {
					model.addAttribute("message", "退勤が二重しています。修正をしてください");
					return "homePage";
				}
			}
			model.addAttribute("message", "退勤しました");
			attendance = record.get();
			attendance.setLeftTime(time);
			attendance.setAttendanceId2(attendanceStatus);
		}
		attendanceRepository.save(attendance);

		return "homePage";
	}

	@GetMapping("/index")
	public String index(
			@RequestParam(name = "menu", defaultValue = "0") Integer menu,
			Model model) {

		switch (menu) {
		case 1:
			return "redirect:/attendanceDetail";
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

	@GetMapping("/attendanceDetail")
	public String attendanceDetail(@RequestParam(name = "month", defaultValue = "") Integer month,
			Model model) {

		int accountId = user.getAccountId();

		List<AttendanceType> attendanceType = attendanceTypeRepository.findAll();

		if (month == null) {
			month = LocalDate.now().getMonthValue();
		}

		List<Date2023> monthDetail = date2023Repository.findByMonthOrderByDateId(month);
		List<Attendance> attendance = attendanceRepository.findByAccountIdOrderByDate(accountId);
		model.addAttribute("month", month);
		model.addAttribute("attendanceType", attendanceType);
		model.addAttribute("monthDetail", monthDetail);
		model.addAttribute("attendance", attendance);
		return "attendance";
	}

	@GetMapping("/edit/{id}/attendance")
	public String editAttendance(
			@PathVariable("id") String id,
			Model model) {
		Optional<Attendance> record = attendanceRepository.findByDateLike(id);

		return "redirect:/attendanceEdit";

	}
}

package com.example.demo.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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

	@GetMapping("/attendance")
	public String attendance(@RequestParam(name = "status") Integer status,
			Model model) {

		String telework = "出社";

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
				telework = "テレワーク";
				attendanceStatus = 1;
			} else if (timeNow.isAfter(time1)) {
				telework = "テレワーク";
				attendanceStatus = 3;
			}
		}
		if (status == 4) {
			if (timeNow.isAfter(time2)) {
				telework = "テレワーク";
				attendanceStatus = 2;
			} else if (timeNow.isBefore(time2)) {
				telework = "テレワーク";
				attendanceStatus = 4;
			}
		}

		String time = timeNow.toString();

		time = time.substring(0, 8);

		if (status == 1 || status == 3) {

			Optional<Attendance> record = attendanceRepository.findByDateAndAccountId(dateNow.toString(),
					user.getAccountId());

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
			return "pending";
		case 3:
			return "redirect:/supervisor";
		case 4:
			return "leave";
		case 5:
			return "leaveRequest";
		case 6:
			return "alternate";
		case 7:
			return "alternateRequest";
		case 8:
			return "redirect:/accountDetail";
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

		Optional<Attendance> record = null;

		Optional<Date2023> record2 = date2023Repository.findByYmd(id);

		date2023 = record2.get();

		record = attendanceRepository.findByDateLike(id);

		if (record.isEmpty() == false) {
			attendance = record.get();
			model.addAttribute("attendance", attendance);
		}

		List<AttendanceType> attendanceType = attendanceTypeRepository.findAll();

		model.addAttribute("date", date2023);

		model.addAttribute("attendanceType", attendanceType);

		return "attendanceEdit";
	}

	@PostMapping("/edit/{idd}")
	public String submitAttendance(
			@PathVariable("idd") Integer id,
			@RequestParam("arrivingTime") Time arrivingTime,
			@RequestParam("leftTime") Time leftTime,
			@RequestParam("attendanceId1") Integer attendanceId1,
			@RequestParam("attendanceId2") Integer attendanceId2,
			@RequestParam("telework") String telework,
			Model model) {

		Optional<Attendance> record = attendanceRepository.findById(id);

		attendance = record.get();
		attendance.setArrivingTime(arrivingTime.toString());
		attendance.setLeftTime(leftTime.toString());
		attendance.setAttendanceId1(attendanceId1);
		attendance.setAttendanceId2(attendanceId2);
		attendance.setTelework(telework);

		String date = attendance.getDate();

		attendanceRepository.save(attendance);

		return "redirect:/edit/" + date + "/attendance";

	}

	@PostMapping("/edit")
	public String newAttendance(
			@RequestParam("ymd") String ymd,
			@RequestParam("arrivingTime") Time arrivingTime,
			@RequestParam("leftTime") Time leftTime,
			@RequestParam("attendanceId1") Integer attendanceId1,
			@RequestParam("attendanceId2") Integer attendanceId2,
			@RequestParam("telework") String telework,
			Model model) {

		attendance = new Attendance(user.getAccountId(), ymd, arrivingTime.toString(), leftTime.toString(),
				attendanceId1, attendanceId2, telework);

		String newDate = attendance.getDate();

		attendanceRepository.save(attendance);

		return "redirect:/edit/" + newDate + "/attendance";
	}

	@GetMapping("/pending")
	public String index(
			Model model) {
		int accountId = user.getAccountId();
		//レポジトリでDBから引き出す？
		
		
		return "redirect:/pending";
	}
}

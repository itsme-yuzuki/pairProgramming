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
import com.example.demo.entity.ApprovalStatus;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.AttendanceType;
import com.example.demo.entity.Date2023;
import com.example.demo.entity.Leave;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.ApprovalStatusRepository;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.AttendanceTypeRepository;
import com.example.demo.repository.Date2023Repository;
import com.example.demo.repository.LeaveRepository;

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

	@Autowired
	Leave leave;

	@Autowired
	LeaveRepository leaveRepository;

	@Autowired
	ApprovalStatus approvalStatus;

	@Autowired
	ApprovalStatusRepository approvalStatusRepository;

	@GetMapping("/attendance")
	public String attendance(@RequestParam(name = "status") Integer status,
			Model model) {

		String telework = "出社";

		LocalDate dateNow = LocalDate.now();

		LocalTime timeNow = LocalTime.now();

		LocalTime time1 = LocalTime.of(9, 00);

		LocalTime time2 = LocalTime.of(17, 30);

		Integer attendanceStatus = null;

		//出退勤区分
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

		//不正出退勤入力判別/メッセージ表示処理
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

	//ヘッダメニュー内容
	@GetMapping("/index")
	public String index(
			@RequestParam(name = "menu", defaultValue = "0") Integer menu,
			Model model) {

		switch (menu) {
		case 1:
			return "redirect:/attendanceDetail";
		case 2:
			return "redirect:/pending";
		case 3:
			return "redirect:/supervisor";
		case 4:
			return "redirect:/leaveDetail";
		case 5:
			return "redirect:/leaveRequest";
		case 6:
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

	//新規休暇申請画面表示
	@GetMapping("/leaveRequest")
	public String leaveRequest(Model model) {

		List<AttendanceType> attendanceType = attendanceTypeRepository.findByAttendanceIdGreaterThan(6);

		model.addAttribute("attendanceType", attendanceType);

		return "leaveRequest";
	}

	//新規休暇休暇申請処理
	@PostMapping("/leaveRequest")
	public String createLeave(@RequestParam(name = "date") LocalDate date,
			@RequestParam(name = "leaveType") Integer leaveType,
			@RequestParam(name = "message") String message,
			@RequestParam(name = "approvalStatus") Integer approvalStatus,
			Model model) {

		Optional<Account> record = accountRepository.findByAccountId(user.getAccountId());

		account = record.get();

		if (account.getAuthoriserId() == null) {
			model.addAttribute("message", "承認者を設定してください");
			return leaveRequest(model);
		}

		leave = new Leave(date.toString(), user.getAccountId(), account.getAuthoriserId(), leaveType,
				approvalStatus, message);
		leaveRepository.save(leave);

		if (approvalStatus == 2) {
			model.addAttribute("message", "休暇申請が完了いたしました");
		} else if (approvalStatus == 1) {
			model.addAttribute("message", "休暇申請を下書きにいたしました");
		}

		return leaveRequest(model);
	}

	@GetMapping("/leaveDetail")
	public String leaveDetail(Model model) {

		List<Leave> leave = leaveRepository.findByAccountId(user.getAccountId());

		List<Account> account = accountRepository.findAll();

		List<ApprovalStatus> approvalStatus = approvalStatusRepository.findAll();

		List<AttendanceType> attendanceType = attendanceTypeRepository.findAll();

		model.addAttribute("account", account);

		model.addAttribute("attendanceType", attendanceType);

		model.addAttribute("approvalStatus", approvalStatus);

		model.addAttribute("leave", leave);

		return "leaveDetail";
	}

	@PostMapping("/leave/{id}/delete")
	public String leaveDelete(@PathVariable("id") Integer id, Model model) {

		leave = leaveRepository.findById(id).get();

		if (leave.getApprovalId() > 2) {
			model.addAttribute("message", "承認済みの休暇申請は削除できません");
			return leaveDetail(model);
		}

		leaveRepository.deleteById(id);

		return leaveDetail(model);
	}

	@PostMapping("/leave/{id}/apply")
	public String leaveApply(@PathVariable("id") Integer id, Model model) {

		leave = leaveRepository.findById(id).get();

		leave.setApprovalId(2);

		leaveRepository.save(leave);

		return leaveDetail(model);
	}

	//アカウントに紐づいている承認申請を一覧表示処理
	@GetMapping("/pending")
	public String index(
			Model model) {
		List<Leave> pendings = leaveRepository.findByAuthoriserIdAndApprovalId(user.getAccountId(), 2);
		List<Account> account = accountRepository.findAll();

		model.addAttribute("pendings", pendings);
		return "pending";
	}

	//申請承認/差し戻し処理
	@PostMapping("/pending/state")
	public String grant(
			@RequestParam("id") Integer id,
			@RequestParam("approvalId") Integer approvalId,
			Model model) {
		Optional<Leave> record = leaveRepository.findById(id);

		leave = record.get();

		leave.setApprovalId(approvalId);

		leaveRepository.save(leave);

		//日付、アカウントID、attendance ID
		Integer accountId = leave.getAccountId();
		String date = leave.getApplyDate();
		Integer leaveId = leave.getLeaveId();
		Attendance attendance = new Attendance(date, accountId, leaveId, leaveId);
		attendanceRepository.save(attendance);

		if (id == 4) {
			model.addAttribute("message", "承認しました");
		}
		if(id == 3) {
			model.addAttribute("message", "差し戻ししました");
		}
		
		return index(model);
	}
	
	

	//	//申請差し戻し処理
	//	@PostMapping("/pending/decline")
	//	public String decline(
	//			@RequestParam("id") Integer id,
	//			@RequestParam("leaveId") Integer leaveId,
	//			Model model) {
	//		Leave leave= new Leave(id,leaveId);
	//		leaveRepository.save(leave);
	//		
	//		System.err.println(leave);
	//		model.addAttribute("message", "差し戻ししました");
	//		
	//		return "redirect:/pending";
	//		
	//	}
}

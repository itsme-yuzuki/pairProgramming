package com.example.demo.entity;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Component
@Entity
@Table(name = "attendance")
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 勤怠ID
	
	@Column(name = "account_id")
	private Integer accountId; // 日付

	@Column(name = "submit_date")
	private LocalDate Date; // 日付

	@Column(name = "submit_time")
	private String time; // 時間

	@Column(name = "attendance_id")
	public Integer attendanceId; // 勤怠状況

	public Integer telework;

	//コンストラクタ	
	public Attendance() {

	}

	public Attendance(Integer accountId, LocalDate date, String time, Integer attendanceId, Integer telework) {
		super();
		this.accountId = accountId;
		Date = date;
		this.time = time;
		this.attendanceId = attendanceId;
		this.telework = telework;
	}

}

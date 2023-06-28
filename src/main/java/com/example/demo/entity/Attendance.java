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
	private LocalDate date; // 日付

	@Column(name = "arriving_time")
	private String arrivingTime; // 時間

	@Column(name = "left_time")
	private String leftTime; // 時間

	@Column(name = "attendance_id")
	public Integer attendanceId; // 勤怠状況

	public Integer telework;

	//コンストラクタ	
	public Attendance() {

	}

	//出勤用
	public Attendance(Integer accountId, LocalDate date, String arrivingTime, String leftTime, Integer attendanceId,
			Integer telework) {
		this.accountId = accountId;
		this.date = date;
		this.arrivingTime = arrivingTime;
		this.leftTime = leftTime;
		this.attendanceId = attendanceId;
		this.telework = telework;
	}

	//退勤用
	public Attendance(Integer id, Integer accountId, LocalDate date, String arrivingTime, String leftTime,
			Integer attendanceId, Integer telework) {
		this.id = id;
		this.accountId = accountId;
		this.date = date;
		this.arrivingTime = arrivingTime;
		this.leftTime = leftTime;
		this.attendanceId = attendanceId;
		this.telework = telework;
	}

}

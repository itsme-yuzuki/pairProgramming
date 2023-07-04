package com.example.demo.entity;

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
	private String date; // 日付

	@Column(name = "arriving_time")
	private String arrivingTime; // 時間

	@Column(name = "left_time")
	private String leftTime; // 時間

	@Column(name = "attendance_id1")
	private Integer attendanceId1; // 出勤状況

	@Column(name = "attendance_id2")
	private Integer attendanceId2; // 出勤状況

	private String telework;

	//コンストラクタ	
	public Attendance() {

	}

	//出勤用
	public Attendance(Integer accountId, String date, String arrivingTime, String leftTime,
			Integer attendanceId1, Integer attendanceId2, String telework) {
		this.accountId = accountId;
		this.date = date;
		this.arrivingTime = arrivingTime;
		this.leftTime = leftTime;
		this.attendanceId1 = attendanceId1;
		this.attendanceId2 = attendanceId2;
		this.telework = telework;
	}

	//退勤用
	public Attendance(Integer id, Integer accountId, String date, String arrivingTime, String leftTime,
			Integer attendanceId1, Integer attendanceId2, String telework) {
		this.id = id;
		this.accountId = accountId;
		this.date = date;
		this.arrivingTime = arrivingTime;
		this.leftTime = leftTime;
		this.attendanceId1 = attendanceId1;
		this.attendanceId2 = attendanceId2;
		this.telework = telework;
	}

}

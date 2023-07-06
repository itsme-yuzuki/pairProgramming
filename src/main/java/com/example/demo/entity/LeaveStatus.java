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
@Table(name = "leave_status")
public class LeaveStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Integer accountId; // 社員番号

	@Column(name = "leave_default")
	private Integer leaveDefault; // 権限

	@Column(name = "leave_remain")
	private Integer leaveRemain; // 権限

	//コンストラクタ	
	public LeaveStatus() {

	}

	public LeaveStatus(Integer leaveDefault, Integer leaveRemain) {
		this.leaveDefault = leaveDefault;
		this.leaveRemain = leaveRemain;
	}

	public LeaveStatus(Integer accountId, Integer leaveDefault, Integer leaveRemain) {
		super();
		this.accountId = accountId;
		this.leaveDefault = leaveDefault;
		this.leaveRemain = leaveRemain;
	}
	
}

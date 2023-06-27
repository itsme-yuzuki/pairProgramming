package com.example.demo.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Component
@Entity
@Table(name = "leave_status")
public class LeaveStatus {
	@Id
	@Column(name = "account_id")
	private Integer accountId; // 社員番号

	@Column(name = "leave_default")
	public Integer leaveDefault; // 権限

	@Column(name = "leave_remain")
	public Integer leaveRemain; // 権限

	//コンストラクタ	
	public LeaveStatus() {

	}

}

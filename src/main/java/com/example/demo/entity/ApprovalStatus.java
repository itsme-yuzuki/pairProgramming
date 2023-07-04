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
@Table(name = "approval_status")
public class ApprovalStatus {
	@Id
	@Column(name = "approval_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer approvalId; // 社員番号

	@Column(name = "approval_status")
	private String approvalStatus; // 名前

	//コンストラクタ	
	public ApprovalStatus() {

	}

}

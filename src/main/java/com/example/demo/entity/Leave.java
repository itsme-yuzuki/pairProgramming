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
@Table(name = "leave")
public class Leave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "apply_date")
	private String applyDate;

	@Column(name = "account_id")
	private Integer accountId;

	@Column(name = "authoriser_id")
	private Integer authoriserId;

	@Column(name = "leave_id")
	private Integer leaveId;

	@Column(name = "approval_id")
	private Integer approvalId;

	private String message;

	public Leave(String applyDate, Integer accountId, Integer authoriserId, Integer leaveId,
			Integer approvalId, String message) {
		this.applyDate = applyDate;
		this.accountId = accountId;
		this.authoriserId = authoriserId;
		this.leaveId = leaveId;
		this.approvalId = approvalId;
		this.message = message;
	}

	public Leave() {
	}

}

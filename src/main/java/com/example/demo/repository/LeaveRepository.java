package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
	
	List<Leave> findByAccountId(Integer accountId);
	
	List<Leave> findByAuthoriserIdAndApprovalId(Integer accountId, Integer ApprovalId);

}

package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.LeaveStatus;

public interface LeaveStatusRepository extends JpaRepository<LeaveStatus, Integer> {
	@Query(value = "SELECT * FROM leave_status WHERE account_id=(SELECT max(account_id) FROM leave_status)" , nativeQuery = true)
	Optional<LeaveStatus>leaveS();

}

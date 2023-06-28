package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	List<Attendance> findByAccountIdOrderByDate(Integer accountId);
}

package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AttendanceType;

public interface AttendanceTypeRepository extends JpaRepository<AttendanceType, Integer> {
	
	List<AttendanceType> findByAttendanceIdGreaterThan(Integer attendanceId);
}

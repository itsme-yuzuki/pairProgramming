package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AttendanceType;

public interface AttendanceTypeRepository extends JpaRepository<AttendanceType, Integer> {
}

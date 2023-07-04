package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.LeaveStatus;

public interface LeaveStatusRepository extends JpaRepository<LeaveStatus, Integer> {

}

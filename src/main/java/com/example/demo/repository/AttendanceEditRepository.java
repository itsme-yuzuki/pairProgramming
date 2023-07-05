package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AttendanceEdit;

public interface AttendanceEditRepository extends JpaRepository<AttendanceEdit, Integer> {
	List<AttendanceEdit> findByAccountIdAndDate(Integer accountId, String date);

	List<AttendanceEdit> findByAccountIdOrderByDate(Integer accoutnId);

	Optional<AttendanceEdit> findByDateAndAccountId(String date, Integer accountId);

	Optional<AttendanceEdit> findByDateLike(String date);
	
	List<AttendanceEdit> findByAccountId(Integer accountId);

}
package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	Optional<Attendance> findByAccountIdAndDate(Integer accountId, String date);

	List<Attendance> findByAccountIdOrderByDate(Integer accoutnId);

	Optional<Attendance> findByDateAndAccountId(String date, Integer accountId);

	Optional<Attendance> findByDateLikeAndAccountId(String date, Integer accountId);

}

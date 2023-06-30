package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	List<Attendance> findByAccountIdOrderByDate(Integer accountId);
	
	Optional<Attendance> findByDate(LocalDate date);
	
	//SELECT submit_date, DATE_PART('dow', submit_date) AS dayofWeek FROM attendance
	@Query("select submit_date, date_part('dow', submit_date) as dayofWeek from attendance = ?1")
}

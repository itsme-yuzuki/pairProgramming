package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Date2023;

public interface Date2023Repository extends JpaRepository<Date2023, Integer> {
	
	List<Date2023> findByMonthOrderByDateId(Integer month);
	
	Optional<Date2023> findByYmd(String ymd);
}

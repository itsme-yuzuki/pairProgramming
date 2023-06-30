package com.example.demo.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Component
@Entity
@Table(name = "date2023")
public class Date2023 {
	
	@Id
	@Column(name = "date_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dateId; 
	
	private String ymd;
	
	private Integer month;
	
	private String weekname;
	
	private String holiday;

	//コンストラクタ	
	public Date2023() {

	}


}

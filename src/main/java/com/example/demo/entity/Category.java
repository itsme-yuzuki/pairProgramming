package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // カテゴリーID

	@Getter
	private String name; // カテゴリー名
}

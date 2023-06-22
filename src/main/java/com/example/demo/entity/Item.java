package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Integer id; // 商品ID

	@Column(name = "category_id")
	@Getter
	private Integer categoryId; // カテゴリーID

	@Getter
	private String name; // 商品名

	@Getter
	private Integer price; // 価格

	@Transient // 永続化対象外
	@Getter
	@Setter
	private Integer quantity; // 数量
}

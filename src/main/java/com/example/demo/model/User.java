package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Data
@Component
@SessionScope
public class User {
	//フィールド
	private String name;
	private Integer id;
	
	//コンストラクタ
	public User() {
		
	}

	public User(String name) {
		this.name = name;
	}
	
	public User(Integer id, String name) {
		this.id= id;
		this.name = name;
	}

}

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
	private Integer accountId;
	private Integer authorise;
	
	//コンストラクタ
	public User() {
		
	}

}

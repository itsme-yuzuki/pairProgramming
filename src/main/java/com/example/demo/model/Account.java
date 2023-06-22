package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Data
@Component
@SessionScope
public class Account {
	//フィールド
	private String name;
	private Integer id;
	
	//コンストラクタ
	public Account() {
		
	}

	public Account(String name) {
		this.name = name;
	}
	
	public Account(String name, String address, String phoneNumber, 
		String email, String password) {
			this.name= name;
			this.name= address;
			this.name= phoneNumber;
			this.name= email;
			this.name= password;
	}
	
	public Account(Integer id, String name) {
		this.id= id;
		this.name = name;
	}

}

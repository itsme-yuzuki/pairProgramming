package com.example.demo.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Component
@Data
@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 社員番号

	private String name; // 名前

	private String email; // メールアドレス

	private String password; //　パスワード

	@Column(name = "authorise_id")
	public Integer authoriseId; // 権限

	//コンストラクタ	
	public Account() {

	}

	public Account(Integer id, String password) {
		this.id = id;
		this.password = password;
	}

	public Account(String name, String email,
			String password, Integer authoriseId) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.authoriseId = authoriseId;
	}

	public Account(Integer id, String name, String email, String password, Integer authoriseId) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authoriseId = authoriseId;
	}

}

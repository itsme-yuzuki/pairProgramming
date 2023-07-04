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
@Table(name = "account")
public class Account {
	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountId; // 社員番号

	private String name; // 名前

	private String email; // メールアドレス

	private String password; //　パスワード

	@Column(name = "authorise_id")
	private Integer authoriseId; // 権限

	@Column(name = "authoriser_id")
	private Integer authoriserId;

	//コンストラクタ	
	public Account() {

	}

	public Account(Integer accountId, String password) {
		this.accountId = accountId;
		this.password = password;
	}

	public Account(String name, String email, String password, Integer authoriseId) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.authoriseId = authoriseId;

	}

	public Account(Integer accountId, String name, String email, String password, Integer authoriseId) {
		this.accountId = accountId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authoriseId = authoriseId;
	}

	public Account(Integer authoriserId) {
		this.authoriserId = authoriserId;
	}

}

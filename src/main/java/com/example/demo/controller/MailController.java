package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailController {

	@Autowired
	private final JavaMailSender javaMailSender;

	MailController(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void send(String email, String password) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setFrom("suzuki1abc1010@gmail.com");
		mailMessage.setSubject("仮パスワード設定のお知らせ");
		mailMessage.setText("勤怠管理システムからのお知らせです。"
				+ "パスワード忘れによる仮パスワード発行を受け付けました。"
				+ "再発行された仮パスワードは下記のとおりです。"
				+ password
				+ "現在は仮パスワードに変更されていますのでこちらでログイン後パスワードの変更を強く推奨いたします。");
		javaMailSender.send(mailMessage);
	}

}

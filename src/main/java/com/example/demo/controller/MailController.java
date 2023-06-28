//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class MailController {
//	private final JavaMailSender javaMailSender;
//	@Autowired
//	MailController(JavaMailSender javaMailSender){
//		this.javaMailSender= javaMailSender;
//	}
//	@RequestMapping(value= "/mail/send", method= {RequestMethod.POST})
//	public String send() {
//		SimpleMailMEssage mailMessage= new SimpleMailMessage();
//		mailMessage.setTo("送信先");
//		mailMessage.setFrom("送信元");
//		mailMessage.setSubject("仮パスワード設定のお知らせ");
//		mailMessage.setText("勤怠管理システムからのお知らせです。"
//				+ "パスワード忘れによる仮パスワード発行を受け付けました。"
//				+ "再発行された仮パスワードは下記のとおりです。"
//				+ ""
//				+ "現在は仮パスワードに変更されていますのでこちらでログイン後パスワードの変更を強く推奨いたします。");
//		javaMailSender.send(mailMessage);
//		return "メール送信しました";
//	}
//	
//}

package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;

@Aspect
@Component
public class Aop {

	@Autowired
	User user;

	// 未ログインの場合ログインページにリダイレクト
	@Around("execution(* com.example.demo.controller.AccountController2.*(..)) ||"
			+ "execution(* com.example.demo.controller.AttendanceController.*(..))")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {

		if (user == null || user.getName() == null
				|| user.getName().length() == 0) {
			System.err.println("ログインしていません!");

			return "redirect:/login?error=notLoggedIn";
		}
		// Controller内のメソッドの実行
		return jp.proceed();
	}

//	@Around("execution(* com.example.demo.controller.AdminController.*(..))")
//	public Object checkAdminLogin(ProceedingJoinPoint jp) throws Throwable {
//
//		if (user == null || user.getName() == null
//				|| user.getName().length() == 0) {
//			System.err.println("ログインしていません!");
//
//			return "redirect:/admin/login?error=notLoggedIn";
//		}
//		// Controller内のメソッドの実行
//		return jp.proceed();
//	}

//	@After("execution(* com.example.demo.controller.*(..)).*(..))")
//	public Object Exception(ProceedingJoinPoint jp)  throws Throwable {
//		Exception e;
//		
//		
//		
//		return "errorPage";
//	}
}

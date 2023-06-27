package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class PasswordResetGenerator {
	static String getRandomString(int i) {
		String theAlphaNumericS;
		StringBuilder builder;

		theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
				+ "0123456789";

		//create the StringBuffer
		builder = new StringBuilder(i);

		for (int m = 0; m < i; m++) {

			// generate numeric
			int myindex = (int) (theAlphaNumericS.length()
					* Math.random());
			//add the characters
			builder.append(theAlphaNumericS
					.charAt(myindex));
		}

		return builder.toString();
	}

	public static String main(String[] args) {
		// the random string length
		int i = 12;

		// output 
		return (getRandomString(i));
		
//		System.out.println("A random string: " + getRandomString(i));
	}
}
package com.infinite.java.parkinglot;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;



public class TestMain {

	public static void main(String[] args) {
		
		Registration_Entity regent=new Registration_Entity();
		
		regent.setName("Akash Kadam");
		regent.setEmail("Akash123@gmail.com");
		try {
			regent.setDob(new SimpleDateFormat("dd-MM-yyyy").parse("12-03-1999"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		regent.setCategory(Category.STAFF);
		regent.setPhoneNumber("9876543212");
		regent.setSecQ1(SecurityQuestions.Q1.toString());
		regent.setAns1("hi");
		regent.setSecQ2(SecurityQuestions.Q2.toString());
		regent.setAns2("hi");
		regent.setSecQ3(SecurityQuestions.Q3.toString());
		regent.setAns3("hi");
		regent.setUserName("Akash_27");
		regent.setPassWord("Akash@123");
		
		
		SessionFactory sf = SessionHelper.getConnection();
		Session se = sf.openSession();
		Transaction tr = se.beginTransaction();
		se.save(regent);
		tr.commit();
		System.out.println("Akash Kadam ");
		
	}
}

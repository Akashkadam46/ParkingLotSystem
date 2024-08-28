package com.infinite.java.parkinglot;

import java.util.Date;
import java.util.Set;

public class Registration_Entity {

	private int rId;
	private String name;
	private String email;
	private String phoneNumber;
	private Date dob;
	private Category category;
	private String secQ1;
	private String ans1;
	private String secQ2;
	private String ans2;
	private String secQ3;
	private String ans3;
	private String userName;
	private String passWord;
	private Slot slot;
	
	
	
//	private Set<Slot> slotList;
	//private String confirmPassword;
	
	
	

	public Slot getSlot() {
		return slot;
	}
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
	public Category[] getCatagories()
	{
		return Category.values();
	}
//	public Set<Slot> getSlotList() {
//		return slotList;
//	}
//	public void setSlotList(Set<Slot> slotList) {
//		this.slotList = slotList;
//	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

//	public String getConfirmPassword() {
//		return confirmPassword;
//	}
//
//	public void setConfirmPassword(String confirmPassword) {
//		this.confirmPassword = confirmPassword;
//	}

	public SecurityQuestions[] getSecurityQuestions(){
		return SecurityQuestions.values();
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public String getSecQ1() {
		return secQ1;
	}

	public void setSecQ1(String secQ1) {
		this.secQ1 = secQ1;
	}

	public String getAns1() {
		return ans1;
	}

	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	public String getSecQ2() {
		return secQ2;
	}

	public void setSecQ2(String secQ2) {
		this.secQ2 = secQ2;
	}

	public String getAns2() {
		return ans2;
	}

	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public String getSecQ3() {
		return secQ3;
	}

	public void setSecQ3(String secQ3) {
		this.secQ3 = secQ3;
	}

	public String getAns3() {
		return ans3;
	}

	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}
	@Override
	public String toString() {
		return "Registration_Entity [rId=" + rId + ", name=" + name + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", dob=" + dob + ", category=" + category + ", secQ1=" + secQ1 + ", ans1=" + ans1
				+ ", secQ2=" + secQ2 + ", ans2=" + ans2 + ", secQ3=" + secQ3 + ", ans3=" + ans3 + ", userName="
				+ userName + ", passWord=" + passWord + "]";
	}

}

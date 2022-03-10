package com.libman.model;


public class User {
   private String firstName;
   private String lastName;
   private String address;
   private int age;
   private String emailId;
   private String passWord;
   private String role;
   private boolean isBanned;
   private int userId;
   private int orgId;
   private Long phoneNo1;
   private Long phoneNo2;
   private int penalty;
   
public Long getPhoneNo2() {
   	return phoneNo2;
}

public void setPhoneNo2(Long phoneNo2) {
   	this.phoneNo2 = phoneNo2;
}
public Long getPhoneNo1() {
	return phoneNo1;
}
public void setPhoneNo1(Long phoneNo1) {
	this.phoneNo1 = phoneNo1;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
public String getPassWord() {
	return passWord;
}
public void setPassWord(String passWord) {
	this.passWord = passWord;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public boolean getIsBanned() {
	return isBanned;
}
public void setIsBanned(boolean isBanned) {
	this.isBanned = isBanned;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public int getOrgId() {
	return orgId;
}
public void setOrgId(int orgId) {
	this.orgId = orgId;
}

public int getPenalty() {
	return penalty;
}

public void setPenalty(int penalty) {
	this.penalty = penalty;
}

}

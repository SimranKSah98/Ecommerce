package com.example.myapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class Customer{

	@SerializedName("password")
	private String password;

//	@SerializedName("phoneNumber")
//	private String phoneNumber;

	@SerializedName("customerEmailId")
	private String emailId;
//
//	@SerializedName("profileImage")
//	private String profileImage;

	@SerializedName("customerName")
	private String customerName;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

//	public void setPhoneNumber(String phoneNumber){
//		this.phoneNumber = phoneNumber;
//	}
//
//	public String getPhoneNumber(){
//		return phoneNumber;
//	}

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

//	public void setProfileImage(String profileImage){
//		this.profileImage = profileImage;
//	}
//
//	public String getProfileImage(){
//		return profileImage;
//	}

	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}

	public String getCustomerName(){
		return customerName;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"password='" + password + '\'' +
				", emailId='" + emailId + '\'' +
				", customerName='" + customerName + '\'' +
				'}';
	}
}
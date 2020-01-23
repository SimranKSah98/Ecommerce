package com.example.myapplication.pojo;


import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("rating")
	private int rating;

	@SerializedName("verified")
	private boolean verified;

	@SerializedName("comment")
	private String comment;

	@SerializedName("customerName")
	private String customerName;

	public void setRating(int rating){
		this.rating = rating;
	}

	public int getRating(){
		return rating;
	}

	public void setVerified(boolean verified){
		this.verified = verified;
	}

	public boolean isVerified(){
		return verified;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}

	public String getCustomerName(){
		return customerName;
	}

	@Override
 	public String toString(){
		return 
			"CommentsItem{" + 
			"rating = '" + rating + '\'' + 
			",verified = '" + verified + '\'' + 
			",comment = '" + comment + '\'' + 
			",customerName = '" + customerName + '\'' + 
			"}";
		}
}
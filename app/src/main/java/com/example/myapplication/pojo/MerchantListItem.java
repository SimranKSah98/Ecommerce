package com.example.myapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class MerchantListItem{

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("price")
	private int price;

	@SerializedName("merchantRating")
	private int merchantRating;

	@SerializedName("merchantName")
	private String merchantName;

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setMerchantRating(int merchantRating){
		this.merchantRating = merchantRating;
	}

	public int getMerchantRating(){
		return merchantRating;
	}

	public void setMerchantName(String merchantName){
		this.merchantName = merchantName;
	}

	public String getMerchantName(){
		return merchantName;
	}

	@Override
 	public String toString(){
		return 
			"MerchantListItem{" + 
			"merchantId = '" + merchantId + '\'' + 
			",price = '" + price + '\'' + 
			",merchantRating = '" + merchantRating + '\'' + 
			",merchantName = '" + merchantName + '\'' + 
			"}";
		}
}
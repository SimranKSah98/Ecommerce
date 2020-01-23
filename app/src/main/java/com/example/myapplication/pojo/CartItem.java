package com.example.myapplication.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CartItem{

	@SerializedName("Products")
	private List<ProductsItem> products;

	@SerializedName("Address")
	private String address;

	@SerializedName("customerId")
	private String customerId;

	@SerializedName("Date")
	private String date;

	public void setProducts(List<ProductsItem> products){
		this.products = products;
	}

	public List<ProductsItem> getProducts(){
		return products;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}

	public String getCustomerId(){
		return customerId;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	@Override
 	public String toString(){
		return 
			"CartItem{" + 
			"products = '" + products + '\'' + 
			",address = '" + address + '\'' + 
			",customerId = '" + customerId + '\'' + 
			",date = '" + date + '\'' + 
			"}";
		}
}
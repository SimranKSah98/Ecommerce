package com.example.myapplication.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class CartResponse{

	@SerializedName("dateOfPlacingOrder")
	private Object dateOfPlacingOrder;

	@SerializedName("productsBought")
	private List<ProductsBoughtItem> productsBought;

	@SerializedName("address")
	private Object address;

	@SerializedName("customerEmailId")
	private String customerEmailId;

	@SerializedName("customerId")
	private String customerId;

	public void setDateOfPlacingOrder(Object dateOfPlacingOrder){
		this.dateOfPlacingOrder = dateOfPlacingOrder;
	}

	public Object getDateOfPlacingOrder(){
		return dateOfPlacingOrder;
	}

	public void setProductsBought(List<ProductsBoughtItem> productsBought){
		this.productsBought = productsBought;
	}

	public List<ProductsBoughtItem> getProductsBought(){
		return productsBought;
	}

	public void setAddress(Object address){
		this.address = address;
	}

	public Object getAddress(){
		return address;
	}

	public void setCustomerEmailId(String customerEmailId){
		this.customerEmailId = customerEmailId;
	}

	public String getCustomerEmailId(){
		return customerEmailId;
	}

	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}

	public String getCustomerId(){
		return customerId;
	}

	@Override
 	public String toString(){
		return 
			"CartResponse{" + 
			"dateOfPlacingOrder = '" + dateOfPlacingOrder + '\'' + 
			",productsBought = '" + productsBought + '\'' + 
			",address = '" + address + '\'' + 
			",customerEmailId = '" + customerEmailId + '\'' + 
			",customerId = '" + customerId + '\'' + 
			"}";
		}
}
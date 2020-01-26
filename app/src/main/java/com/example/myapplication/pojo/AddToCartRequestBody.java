package com.example.myapplication.pojo;
import com.google.gson.annotations.SerializedName;

public class AddToCartRequestBody{

	@SerializedName("image")
	private String image;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("productId")
	private String productId;

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("price")
	private double price;


	@SerializedName("name")
	private String name;

	@SerializedName("merchantName")
	private String merchantName;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}



	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMerchantName(String merchantName){
		this.merchantName = merchantName;
	}

	public String getMerchantName(){
		return merchantName;
	}

	@Override
	public String toString() {
		return "AddToCartRequestBody{" +
				"image='" + image + '\'' +
				", quantity=" + quantity +
				", productId='" + productId + '\'' +
				", merchantId='" + merchantId + '\'' +
				", price=" + price +
				", name='" + name + '\'' +
				", merchantName='" + merchantName + '\'' +
				'}';
	}
}
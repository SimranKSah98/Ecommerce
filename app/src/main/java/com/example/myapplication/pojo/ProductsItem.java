package com.example.myapplication.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ProductsItem implements Serializable {

	@SerializedName("id")
	private String id;


	@SerializedName("image")
	private String image;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("price")
	private Double price;

	@SerializedName("name")
	private String name;

	@SerializedName("productdescription")
	private ProductDescription productDescription;


	public ProductDescription getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(ProductDescription productDescription) {
		this.productDescription = productDescription;
	}

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

	public String getId() {
		return id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

//	@Override
// 	public String toString(){
//		return
//			"ProductsItem{" +
//			"image = '" + image + '\'' +
//			",quantity = '" + quantity + '\'' +
//			",price = '" + price + '\'' +
//			",name = '" + name + '\'' +
//			"}";
//		}
}
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
	private int price;

	@SerializedName("name")
	private String name;

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

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
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
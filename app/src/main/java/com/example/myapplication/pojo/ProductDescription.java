package com.example.myapplication.pojo;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class ProductDescription{


	public ProductsItem getProductsItem() {
		return productsItem;
	}

	public void setProductsItem(ProductsItem productsItem) {
		this.productsItem = productsItem;
	}

	@SerializedName("productItem")
	private ProductsItem productsItem;


	@SerializedName("image")
	private String image;

	@SerializedName("usp")
	private String usp;

	@SerializedName("comments")
	private List<CommentsItem> comments;

	@SerializedName("merchantRating")
	private double merchantRating;

	@SerializedName("price")
	private double price;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("avgRating")
	private double avgRating;

	@SerializedName("attributes")
	private Map<String,String> attributes;

	@SerializedName("stock")
	private boolean stock;

	@SerializedName("merchantName")
	private String merchantName;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setUsp(String usp){
		this.usp = usp;
	}

	public String getUsp(){
		return usp;
	}

	public void setComments(List<CommentsItem> comments){
		this.comments = comments;
	}

	public List<CommentsItem> getComments(){
		return comments;
	}

	public void setMechantRating(double mechantRating){
		this.merchantRating = mechantRating;
	}

	public double getMechantRating(){
		return merchantRating;
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

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setAvgRating(double avgRating){
		this.avgRating = avgRating;
	}

	public double getAvgRating(){
		return avgRating;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void setStock(boolean stock){
		this.stock = stock;
	}

	public boolean isStock(){
		return stock;
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
			"ProductDescription{" + 
			"image = '" + image + '\'' + 
			",usp = '" + usp + '\'' + 
			",comments = '" + comments + '\'' + 
			",mechantRating = '" + merchantRating + '\'' +
			",price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",avgRating = '" + avgRating + '\'' + 
			",attributes = '" + attributes + '\'' + 
			",stock = '" + stock + '\'' + 
			",merchantName = '" + merchantName + '\'' + 
			"}";
		}
}
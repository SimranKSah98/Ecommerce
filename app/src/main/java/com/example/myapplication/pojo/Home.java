package com.example.myapplication.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Home {

	@SerializedName("categoryList")
	private List<String> categoryList;

	@SerializedName("cartCount")
	private int cartCount;

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	public void setCategoryList(List<String> categoryList){
		this.categoryList = categoryList;
	}

	public List<String> getCategoryList(){
		return categoryList;
	}

	public void setCartCount(int cartCount){
		this.cartCount = cartCount;
	}

	public int getCartCount(){
		return cartCount;
	}

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	@Override
 	public String toString(){
		return 
			"Home{" + 
			"categoryList = '" + categoryList + '\'' + 
			",cartCount = '" + cartCount + '\'' + 
			",categories = '" + categories + '\'' + 
			"}";
		}
}
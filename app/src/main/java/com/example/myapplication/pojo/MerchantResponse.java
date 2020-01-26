package com.example.myapplication.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class MerchantResponse{

	@SerializedName("merchantList")
	private List<MerchantListItem> merchantList;

	public void setMerchantList(List<MerchantListItem> merchantList){
		this.merchantList = merchantList;
	}

	public List<MerchantListItem> getMerchantList(){
		return merchantList;
	}

	@Override
 	public String toString(){
		return 
			"MerchantResponse{" + 
			"merchantList = '" + merchantList + '\'' + 
			"}";
		}
}
package com.example.thecoffeehouse.data.model.product;

import com.google.gson.annotations.SerializedName;

public class VariantsItem{

	@SerializedName("val")
	private String val;

	@SerializedName("localize")
	private Localize localize;

	@SerializedName("code")
	private String code;

	@SerializedName("price")
	private int price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("is_main")
	private boolean isMain;

	public void setVal(String val){
		this.val = val;
	}

	public String getVal(){
		return val;
	}

	public void setLocalize(Localize localize){
		this.localize = localize;
	}

	public Localize getLocalize(){
		return localize;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setIsMain(boolean isMain){
		this.isMain = isMain;
	}

	public boolean isIsMain(){
		return isMain;
	}
}
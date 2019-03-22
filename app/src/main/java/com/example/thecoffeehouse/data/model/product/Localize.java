package com.example.thecoffeehouse.data.model.product;

import com.google.gson.annotations.SerializedName;

public class Localize{

	@SerializedName("vi")
	private String vi;

	@SerializedName("en")
	private String en;

	public void setVi(String vi){
		this.vi = vi;
	}

	public String getVi(){
		return vi;
	}

	public void setEn(String en){
		this.en = en;
	}

	public String getEn(){
		return en;
	}

	@Override
 	public String toString(){
		return 
			"Localize{" + 
			"vi = '" + vi + '\'' + 
			",en = '" + en + '\'' + 
			"}";
		}
}
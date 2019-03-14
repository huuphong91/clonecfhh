package com.example.thecoffeehouse.data.model.product;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OptionsItem{

	@SerializedName("desciption")
	private String desciption;

	@SerializedName("min")
	private int min;

	@SerializedName("default_index")
	private int defaultIndex;

	@SerializedName("max")
	private int max;

	@SerializedName("name")
	private String name;

	@SerializedName("items")
	private List<ItemsItem> items;

	public void setDesciption(String desciption){
		this.desciption = desciption;
	}

	public String getDesciption(){
		return desciption;
	}

	public void setMin(int min){
		this.min = min;
	}

	public int getMin(){
		return min;
	}

	public void setDefaultIndex(int defaultIndex){
		this.defaultIndex = defaultIndex;
	}

	public int getDefaultIndex(){
		return defaultIndex;
	}

	public void setMax(int max){
		this.max = max;
	}

	public int getMax(){
		return max;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}
}
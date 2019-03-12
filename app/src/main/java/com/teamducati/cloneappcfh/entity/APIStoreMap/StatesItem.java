package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class  StatesItem{

	@SerializedName("name")
	private String name;

	@SerializedName("count")
	private int count;

	@SerializedName("districts")
	private List<DistrictsItem> districts;

	@SerializedName("id")
	private int id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setDistricts(List<DistrictsItem> districts){
		this.districts = districts;
	}

	public List<DistrictsItem> getDistricts(){
		return districts;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"StatesItem{" + 
			"name = '" + name + '\'' + 
			",count = '" + count + '\'' + 
			",districts = '" + districts + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
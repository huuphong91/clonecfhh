package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistrictsItem{

	@SerializedName("state_name")
	private String stateName;

	@SerializedName("stores")
	private List<StoresItem> stores;

	@SerializedName("name")
	private String name;

	@SerializedName("count")
	private int count;

	@SerializedName("id")
	private int id;

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public void setStores(List<StoresItem> stores){
		this.stores = stores;
	}

	public List<StoresItem> getStores(){
		return stores;
	}

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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"DistrictsItem{" + 
			"state_name = '" + stateName + '\'' + 
			",stores = '" + stores + '\'' + 
			",name = '" + name + '\'' + 
			",count = '" + count + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
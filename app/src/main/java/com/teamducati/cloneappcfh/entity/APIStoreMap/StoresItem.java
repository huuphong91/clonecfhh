package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoresItem{

	@SerializedName("images")
	private List<String> images;

	@SerializedName("address")
	private Address address;

	@SerializedName("distance")
	private String distance;

	@SerializedName("phone")
	private String phone;

	@SerializedName("closing_time")
	private String closingTime;

	@SerializedName("opening_time")
	private String openingTime;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	@SerializedName("longitude")
	private String longitude;

	public void setImages(List<String> images){
		this.images = images;
	}

	public List<String> getImages(){
		return images;
	}

	public void setAddress(Address address){
		this.address = address;
	}

	public Address getAddress(){
		return address;
	}

	public void setDistance(String distance){
		this.distance = distance;
	}

	public String getDistance(){
		return distance;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setClosingTime(String closingTime){
		this.closingTime = closingTime;
	}

	public String getClosingTime(){
		return closingTime;
	}

	public void setOpeningTime(String openingTime){
		this.openingTime = openingTime;
	}

	public String getOpeningTime(){
		return openingTime;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"StoresItem{" + 
			"images = '" + images + '\'' + 
			",address = '" + address + '\'' + 
			",distance = '" + distance + '\'' + 
			",phone = '" + phone + '\'' + 
			",closing_time = '" + closingTime + '\'' + 
			",opening_time = '" + openingTime + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}
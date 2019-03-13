package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

public class Address{

	@SerializedName("country")
	private String country;

	@SerializedName("street")
	private String street;

	@SerializedName("district")
	private String district;

	@SerializedName("ward")
	private String ward;

	@SerializedName("state")
	private String state;

	@SerializedName("full_address")
	private String fullAddress;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setDistrict(String district){
		this.district = district;
	}

	public String getDistrict(){
		return district;
	}

	public void setWard(String ward){
		this.ward = ward;
	}

	public String getWard(){
		return ward;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setFullAddress(String fullAddress){
		this.fullAddress = fullAddress;
	}

	public String getFullAddress(){
		return fullAddress;
	}

	@Override
 	public String toString(){
		return 
			"Address{" + 
			"country = '" + country + '\'' + 
			",street = '" + street + '\'' + 
			",district = '" + district + '\'' + 
			",ward = '" + ward + '\'' + 
			",state = '" + state + '\'' + 
			",full_address = '" + fullAddress + '\'' + 
			"}";
		}
}
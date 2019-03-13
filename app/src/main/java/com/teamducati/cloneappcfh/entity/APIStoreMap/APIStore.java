package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIStore {

	@SerializedName("states")
	private List<StatesItem> states;

	public void setStates(List<StatesItem> states){
		this.states = states;
	}

	public List<StatesItem> getStates(){
		return states;
	}

	@Override
 	public String toString(){
		return 
			"APIStore{" +
			"states = '" + states + '\'' + 
			"}";
		}
}
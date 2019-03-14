package com.example.thecoffeehouse.data.model.product;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Order{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("suggestion")
	private List<SuggestionItem> suggestion;

	@SerializedName("message")
	private String message;

	@SerializedName("key")
	private String key;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setSuggestion(List<SuggestionItem> suggestion){
		this.suggestion = suggestion;
	}

	public List<SuggestionItem> getSuggestion(){
		return suggestion;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}
}
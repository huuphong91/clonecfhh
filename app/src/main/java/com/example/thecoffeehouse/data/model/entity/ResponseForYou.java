package com.example.thecoffeehouse.data.model.entity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseForYou implements Serializable {

	@SerializedName("date")
	private String date;

	@SerializedName("button")
	private String button;

	@SerializedName("image")
	private String image;

	@SerializedName("effects")
	private String effects;

	@SerializedName("deeplink")
	private String deeplink;

	@SerializedName("action")
	private Object action;

	@SerializedName("shareUrl")
	private String shareUrl;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	@SerializedName("content")
	private String content;

	@SerializedName("key")
	private Object key;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setButton(String button){
		this.button = button;
	}

	public String getButton(){
		return button;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setEffects(String effects){
		this.effects = effects;
	}

	public String getEffects(){
		return effects;
	}

	public void setDeeplink(String deeplink){
		this.deeplink = deeplink;
	}

	public String getDeeplink(){
		return deeplink;
	}

	public void setAction(Object action){
		this.action = action;
	}

	public Object getAction(){
		return action;
	}

	public void setShareUrl(String shareUrl){
		this.shareUrl = shareUrl;
	}

	public String getShareUrl(){
		return shareUrl;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setKey(Object key){
		this.key = key;
	}

	public Object getKey(){
		return key;
	}

	@Override
 	public String toString(){
		return 
			"ResponseForYou{" + 
			"date = '" + date + '\'' + 
			",button = '" + button + '\'' + 
			",image = '" + image + '\'' + 
			",effects = '" + effects + '\'' + 
			",deeplink = '" + deeplink + '\'' + 
			",action = '" + action + '\'' + 
			",shareUrl = '" + shareUrl + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",url = '" + url + '\'' + 
			",content = '" + content + '\'' + 
			",key = '" + key + '\'' + 
			"}";
		}
}
package com.example.thecoffeehouse.data.model.product;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SuggestionItem{

	@SerializedName("categ_id")
	private List<Integer> categId;

	@SerializedName("image")
	private String image;

	@SerializedName("localize")
	private Localize localize;

	@SerializedName("topping_list")
	private List<Object> toppingList;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("active")
	private boolean active;

	@SerializedName("variants")
	private List<VariantsItem> variants;

	@SerializedName("type")
	private List<Object> type;

	@SerializedName("product_name")
	private String productName;

	@SerializedName("branch")
	private String branch;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("price")
	private int price;

	@SerializedName("base_price")
	private int basePrice;

	@SerializedName("options")
	private List<OptionsItem> options;

	@SerializedName("_id")
	private String id;

	@SerializedName("image_id")
	private int imageId;

	public void setCategId(List<Integer> categId){
		this.categId = categId;
	}

	public List<Integer> getCategId(){
		return categId;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setLocalize(Localize localize){
		this.localize = localize;
	}

	public Localize getLocalize(){
		return localize;
	}

	public void setToppingList(List<Object> toppingList){
		this.toppingList = toppingList;
	}

	public List<Object> getToppingList(){
		return toppingList;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setActive(boolean active){
		this.active = active;
	}

	public boolean isActive(){
		return active;
	}

	public void setVariants(List<VariantsItem> variants){
		this.variants = variants;
	}

	public List<VariantsItem> getVariants(){
		return variants;
	}

	public void setType(List<Object> type){
		this.type = type;
	}

	public List<Object> getType(){
		return type;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setBranch(String branch){
		this.branch = branch;
	}

	public String getBranch(){
		return branch;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setBasePrice(int basePrice){
		this.basePrice = basePrice;
	}

	public int getBasePrice(){
		return basePrice;
	}

	public void setOptions(List<OptionsItem> options){
		this.options = options;
	}

	public List<OptionsItem> getOptions(){
		return options;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setImageId(int imageId){
		this.imageId = imageId;
	}

	public int getImageId(){
		return imageId;
	}
}
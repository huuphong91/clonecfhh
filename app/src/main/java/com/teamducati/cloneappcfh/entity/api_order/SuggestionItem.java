package com.teamducati.cloneappcfh.entity.api_order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionItem {

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
}
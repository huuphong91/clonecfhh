package com.teamducati.cloneappcfh.entity.api_order;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemsItem {

    @SerializedName("val")
    private String val;
    @SerializedName("localize")
    private Localize localize;
    @SerializedName("code")
    private String code;
    @SerializedName("price")
    private int price;
    @SerializedName("product_id")
    private int productId;
    @SerializedName("is_main")
    private boolean isMain;
}
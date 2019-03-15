package com.teamducati.cloneappcfh.entity.api_order;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToppingListItem {

    @SerializedName("code")
    private String code;
    @SerializedName("price")
    private int price;
    @SerializedName("product_id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
}
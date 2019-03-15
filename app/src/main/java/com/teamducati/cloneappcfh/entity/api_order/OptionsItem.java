package com.teamducati.cloneappcfh.entity.api_order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionsItem {

    @SerializedName("desciption")
    private String desciption;
    @SerializedName("min")
    private int min;
    @SerializedName("default_index")
    private int defaultIndex;
    @SerializedName("max")
    private int max;
    @SerializedName("name")
    private String name;
    @SerializedName("items")
    private List<ItemsItem> items;
}
package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DistrictsItem {

    @SerializedName("state_name")
    private String stateName;
    @SerializedName("stores")
    private List<StoresItem> stores;
    @SerializedName("name")
    private String name;
    @SerializedName("count")
    private int count;
    @SerializedName("id")
    private int id;
}
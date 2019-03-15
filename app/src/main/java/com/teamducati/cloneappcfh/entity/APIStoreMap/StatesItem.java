package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatesItem {

    @SerializedName("name")
    private String name;
    @SerializedName("count")
    private int count;
    @SerializedName("districts")
    private List<DistrictsItem> districts;
    @SerializedName("id")
    private int id;
}
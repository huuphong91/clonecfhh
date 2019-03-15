package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoresItem {

    @SerializedName("images")
    private List<String> images;
    @SerializedName("address")
    private Address address;
    @SerializedName("distance")
    private String distance;
    @SerializedName("phone")
    private String phone;
    @SerializedName("closing_time")
    private String closingTime;
    @SerializedName("opening_time")
    private String openingTime;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;
    @SerializedName("status")
    private String status;
    @SerializedName("longitude")
    private String longitude;
}
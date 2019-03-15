package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    @SerializedName("country")
    private String country;
    @SerializedName("street")
    private String street;
    @SerializedName("district")
    private String district;
    @SerializedName("ward")
    private String ward;
    @SerializedName("state")
    private String state;
    @SerializedName("full_address")
    private String fullAddress;
}
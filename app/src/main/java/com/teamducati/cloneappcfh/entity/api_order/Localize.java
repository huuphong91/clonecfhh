package com.teamducati.cloneappcfh.entity.api_order;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Localize {

    @SerializedName("vi")
    private String vi;
    @SerializedName("en")
    private String en;
}
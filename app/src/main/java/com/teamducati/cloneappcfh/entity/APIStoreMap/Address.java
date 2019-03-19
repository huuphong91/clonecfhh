package com.teamducati.cloneappcfh.entity.APIStoreMap;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String positionShipTitle;
    private String imgUrl;

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

    public String getPositionShipTitle() {
        return positionShipTitle;
    }

    public void setPositionShipTitle(String positionShipTitle) {
        this.positionShipTitle = positionShipTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
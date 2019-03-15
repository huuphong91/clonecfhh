package com.example.thecoffeehouse.data.model.store;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Store implements Comparable<Store>{

    @SerializedName("id")
    public int storeId;
    @SerializedName("name")
    public String storeName;
    //@SerializedName("distance") Không lấy Distance từ API
    public int storeDistance;
    @SerializedName("address")
    public StoreAddress storeAddress;
    @SerializedName("phone")
    public String storePhone;
    @SerializedName("opening_time")
    public String storeOpenTime;
    @SerializedName("closing_time")
    public String storeCloseTime;
    @SerializedName("status")
    public String storeStatus;
    @SerializedName("images")
    public List<String> storeImages;
    @SerializedName("latitude")
    public double storeLat;
    @SerializedName("longitude")
    public double storeLong;

    @Override
    public int compareTo(Store o) {
        return o.storeDistance - storeDistance;
    }

    public class StoreAddress{
        @SerializedName("street")
        public String street;
        @SerializedName("ward")
        public String ward;
        @SerializedName("district")
        public String district;
        @SerializedName("state")
        public String state;
        @SerializedName("country")
        public String country;
        @SerializedName("full_address")
        public String full_address;
    }
}

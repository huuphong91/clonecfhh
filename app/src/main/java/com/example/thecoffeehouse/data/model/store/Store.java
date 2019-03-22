package com.example.thecoffeehouse.data.model.store;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "store")
public class Store implements Comparable<Store>, Serializable {

    @PrimaryKey
    @SerializedName("id")
    public int storeId;
    @SerializedName("name")
    public String storeName;
    //@SerializedName("distance") Không lấy Distance từ API
    public int storeDistance;
    @SerializedName("address")
    @Embedded(prefix = "store_")
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
    @TypeConverters({ListStringConverter.class})
    public List<String> storeImages;
    @SerializedName("latitude")
    public double storeLat;
    @SerializedName("longitude")
    public double storeLong;

    @Override
    public int compareTo(Store o) {
        return o.storeDistance - storeDistance;
    }

    public static class StoreAddress implements Serializable {
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

    // converter từ List<String> to String
    static final class ListStringConverter {
        @TypeConverter
        public String fromArray(List<String> strings) {
            String string = "";
            for (String s : strings) string += (s + ",");

            return string;
        }

        @TypeConverter
        public List<String> toArray(String concatenatedStrings) {
            List<String> myStrings = new ArrayList<>();
            Collections.addAll(myStrings, concatenatedStrings.split(","));
            return myStrings;
        }
    }
}

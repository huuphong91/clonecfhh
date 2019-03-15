package com.example.thecoffeehouse.data.model.store;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreResponeObject {
    @SerializedName("states")
    public List<State> listState;

    public class State {
        @SerializedName("id")
        public int stateId;
        @SerializedName("name")
        public String stateName;
        @SerializedName("count")
        public int stateCount;
        @SerializedName("districts")
        public List<District> districts;
    }

    public class District {
        @SerializedName("id")
        public int districId;
        @SerializedName("name")
        public String districtName;
        @SerializedName("count")
        public int districtCount;
        @SerializedName("state_name")
        public String stateName;
        @SerializedName("stores")
        public List<Store> stores;
    }
}

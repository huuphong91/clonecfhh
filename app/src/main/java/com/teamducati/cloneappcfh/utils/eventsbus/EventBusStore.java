package com.teamducati.cloneappcfh.utils.eventsbus;

import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;
import java.util.List;

public class EventBusStore {

    public int position;
    public Double lat;
    public Double lon;
    public String name;
    public String address;
    public List<StoresItem> storesItems;

    public EventBusStore(Double lat, Double lon, String name, String address,
                         List<StoresItem> storesItems, int position) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.address = address;
        this.storesItems = storesItems;
        this.position = position;
    }
}

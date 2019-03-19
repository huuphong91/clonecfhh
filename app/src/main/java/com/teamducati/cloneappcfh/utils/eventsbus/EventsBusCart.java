package com.teamducati.cloneappcfh.utils.eventsbus;

import com.teamducati.cloneappcfh.entity.api_order.DataItem;

public class EventsBusCart {

    public DataItem dataItem;
    public int numberProduct;
    public int price;

    public EventsBusCart(DataItem dataItem, int numberProduct, int price) {
        this.dataItem = dataItem;
        this.numberProduct = numberProduct;
        this.price = price;
    }
}

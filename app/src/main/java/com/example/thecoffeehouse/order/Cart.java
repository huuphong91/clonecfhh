package com.example.thecoffeehouse.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cart implements Serializable {
    @SerializedName("count")
    private int count;
    @SerializedName ("name")
    private String name;
    @SerializedName ("size")
    private String size;
    @SerializedName ("total")
    private int total;

    public Cart(int count, String name, String size, int total) {
        this.count = count;
        this.name = name;
        this.size = size;
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

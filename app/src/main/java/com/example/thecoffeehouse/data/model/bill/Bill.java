package com.example.thecoffeehouse.data.model.bill;

public class Bill {
    private int totalPrice;
    private int cupCount;
    private int timeCount;

    public Bill(int totalPrice, int cupCount, int timeCount) {
        this.totalPrice = totalPrice;
        this.cupCount = cupCount;
        this.timeCount = timeCount;
    }

    public Bill() {
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCupCount() {
        return cupCount;
    }

    public void setCupCount(int cupCount) {
        this.cupCount = cupCount;
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }
}

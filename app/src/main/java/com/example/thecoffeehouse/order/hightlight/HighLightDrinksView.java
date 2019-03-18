package com.example.thecoffeehouse.order.hightlight;

import com.example.thecoffeehouse.data.model.product.DataItem;

import java.util.List;

public interface HighLightDrinksView {
    void showToast(String s);

    void displayProduct(List<DataItem> dataItem);

    void displayError(String s);
}

package com.example.thecoffeehouse.order.search;

import com.example.thecoffeehouse.data.model.product.DataItem;

import java.util.List;

public interface SearchView {

    void showToast(String s);

    void displayProduct(List<DataItem> dataItem);

    void displayError(String s);
}

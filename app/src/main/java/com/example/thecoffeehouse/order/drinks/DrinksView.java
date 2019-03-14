package com.example.thecoffeehouse.order.drinks;


import com.example.thecoffeehouse.data.model.product.DataItem;

import java.util.List;

public interface DrinksView {

    void showToast(String s);

    void displayProduct(List<DataItem> orderResponse);

    void displayError(String s);
}

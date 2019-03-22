package com.example.thecoffeehouse.order.filter;

import com.example.thecoffeehouse.data.model.product.Category;

import java.util.List;

public interface FilterView {

    void getCategory(List<Category> categoryList);
}

package com.example.thecoffeehouse.news.viewnews;

import com.example.thecoffeehouse.data.model.entity.ResponseNews;

import java.util.List;

public interface NewsView {

    void displayNews(List<ResponseNews> newsResponse);
    void displayError(String s);
}

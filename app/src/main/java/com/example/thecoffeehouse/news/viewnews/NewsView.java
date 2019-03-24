package com.example.thecoffeehouse.news.viewnews;

import android.app.Activity;

import com.example.thecoffeehouse.data.model.entity.ResponseNews;

import java.util.List;

public interface NewsView {
    void displayNews(List<ResponseNews> newsReponseList);
    void onError(Throwable throwable);
    Activity getActivity();
}

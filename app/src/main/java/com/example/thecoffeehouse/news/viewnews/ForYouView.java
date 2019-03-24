package com.example.thecoffeehouse.news.viewnews;

import android.app.Activity;

import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;

import java.util.List;

public interface ForYouView {
    void displayForYou(List<ResponseForYou> responseForYouList);
    void onError(Throwable throwable);
    Activity getActivity();

}

package com.example.thecoffeehouse.news.viewnews;

import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import java.util.List;

public interface ForYouView {
    void displayForYou(List<ResponseForYou> newsResponse);
    void displayError(String s);
}

package com.example.thecoffeehouse.news.presenter;

public interface NewsPresenter {
    void getNews();
    void loadNewsForFromDatabase();
    void onDestroy();
    void onStop();

}

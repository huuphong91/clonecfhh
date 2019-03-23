package com.example.thecoffeehouse.news.presenter;

public interface ForYouPresenter {
    void getForYou();
    void loadListNewsFromDatabase();
    void onDestroy();
    void onStop();

}

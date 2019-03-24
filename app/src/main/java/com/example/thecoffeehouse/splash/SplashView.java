package com.example.thecoffeehouse.splash;

public interface SplashView {
    void onLoadStoreSuccess();
    void onLoadNewsSuccess();
    void OnLoadNewsPromotionSuccess();
    void OnError(Throwable throwable);
}

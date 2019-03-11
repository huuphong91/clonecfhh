package com.teamducati.cloneappcfh.screen.news;

public class NewsPresenter implements NewsContract.Presenter {

    private NewsContract.View mNewsView;

    public NewsPresenter(NewsContract.View newsView) {
        this.mNewsView = newsView;
        mNewsView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}

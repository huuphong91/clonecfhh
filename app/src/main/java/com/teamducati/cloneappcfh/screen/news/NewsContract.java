package com.teamducati.cloneappcfh.screen.news;

import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface NewsContract {

    interface View extends BaseView<Presenter> {

        public void showListNewsPromotion(List<NewsPromotion> arrayList);

        public void showListNews(List<News> arrayList);

        public void handleSuccess();

        public void handleError();
    }

    interface Presenter extends BasePresenter {

        public void getAllListNewsPromotion();

        public void getAllListNews();


    }
}

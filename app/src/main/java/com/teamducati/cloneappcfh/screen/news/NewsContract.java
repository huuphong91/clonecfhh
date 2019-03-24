package com.teamducati.cloneappcfh.screen.news;

import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

import java.util.List;

public interface NewsContract {

    interface View extends BaseView<Presenter> {

        void getListNewsPromotion(List<NewsPromotion> arrayList);

        void getListNews(List<News> arrayList);

        void getHandleSuccess();

        void getHandleError();

        void showUser(User user);
    }

    interface Presenter extends BasePresenter<View> {

        void onAllListNewsPromotion();

        void onAllListNews();
    }
}

package com.teamducati.cloneappcfh.screen.store;

import com.teamducati.cloneappcfh.screen.news.NewsContract;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

public interface StoreContract {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<NewsContract.Presenter> {
    }
}

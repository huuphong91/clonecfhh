package com.teamducati.cloneappcfh.screen.news.notification;

import com.teamducati.cloneappcfh.entity.Notification;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

import java.util.List;

public interface NoticationContract {

    interface View extends BaseView<Presenter> {
        void getListNotification(List<Notification> arrayList);
    }

    interface Presenter extends BasePresenter {
        void onAllListNotification();
    }
}

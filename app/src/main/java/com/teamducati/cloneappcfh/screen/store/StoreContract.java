package com.teamducati.cloneappcfh.screen.store;

import com.teamducati.cloneappcfh.entity.APIStoreMap.StatesItem;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;
import java.util.List;

public interface StoreContract {

    interface Presenter extends BasePresenter<View> {
        void onGetAllStore();

        void onGetAllProvince();
    }

    interface View extends BaseView<Presenter> {
        void showListStore(List<StoresItem> arrayList);

        void showListProvince(List<StatesItem> arrayList);
    }
}

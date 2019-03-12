package com.teamducati.cloneappcfh.screen.store;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

import java.util.List;

public interface StoreContract {

    interface Presenter extends BasePresenter {
        void onAddMarker();
        BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId);
        void onGetAllStore();

    }

    interface View extends BaseView<Presenter> {
        void addMarker();
        void showListStore(List<StoresItem> arrayList);
    }
}

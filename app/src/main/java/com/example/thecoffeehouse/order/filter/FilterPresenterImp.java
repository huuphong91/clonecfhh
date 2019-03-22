package com.example.thecoffeehouse.order.filter;

import android.app.Application;

import com.example.thecoffeehouse.data.ApiHandler;
import com.example.thecoffeehouse.data.AppRepository;
import com.example.thecoffeehouse.data.AppRespositoryImp;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FilterPresenterImp implements FilterPresenter {

    private AppRepository appRepository;
    private FilterView filterView;

    public FilterPresenterImp(Application application, FilterView filterView) {
        this.filterView = filterView;
        appRepository = new AppRespositoryImp (application);
    }

    @Override
    public void getCategory() {
        ApiHandler.getInstance ().getAppApi ().getCategory ()
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (categoryList -> {
                    filterView.getCategory (categoryList);
                });
    }
}

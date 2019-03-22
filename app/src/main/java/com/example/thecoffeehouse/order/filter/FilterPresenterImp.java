package com.example.thecoffeehouse.order.filter;

import com.example.thecoffeehouse.data.ApiHandler;
import com.example.thecoffeehouse.data.AppRepository;
import com.example.thecoffeehouse.data.AppRespositoryImp;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FilterPresenterImp implements FilterPresenter {

    private AppRepository appRepository;
    private FilterView filterView;

    public FilterPresenterImp(FilterView filterView) {
        this.filterView = filterView;
        appRepository = new AppRespositoryImp ();
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

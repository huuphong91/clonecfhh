package com.teamducati.cloneappcfh.utils;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}

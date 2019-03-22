package com.example.thecoffeehouse;

import rx.Observable;
import rx.subjects.PublishSubject;

public class RxBus {

    private static RxBus sInstance;

    private PublishSubject<Object> mPublishSubject;

    private RxBus() {
        mPublishSubject = PublishSubject.create();
    }

    public static RxBus getInstance() {
        if(sInstance == null) {
            sInstance = new RxBus();
        }
        return sInstance;
    }

    public void postEvent(Object object) {
        mPublishSubject.onNext(object);
    }

    public Observable getObservable() {
        return mPublishSubject.asObservable();
    }
}
